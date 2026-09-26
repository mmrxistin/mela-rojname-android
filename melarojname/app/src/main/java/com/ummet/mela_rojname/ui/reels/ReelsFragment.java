package com.ummet.mela_rojname.ui.reels;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.PagerSnapHelper;

import com.ummet.mela_rojname.data.SessionManager;
import com.ummet.mela_rojname.databinding.FragmentReelsBinding;
import com.ummet.mela_rojname.model.Gender;
import com.ummet.mela_rojname.model.Reel;
import com.ummet.mela_rojname.model.User;
import com.ummet.mela_rojname.network.ApiClient;
import com.ummet.mela_rojname.network.ApiService;
import com.ummet.mela_rojname.security.GenderGuard;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReelsFragment extends Fragment {

    private FragmentReelsBinding binding;
    private ReelAdapter adapter;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReelsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager = new SessionManager(requireContext());
        adapter = new ReelAdapter();

        binding.rvReels.setAdapter(adapter);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.rvReels);

        loadReels();
    }

    private void loadReels() {
        User currentUser = sessionManager.getCurrentUser();
        ApiService apiService = ApiClient.getInstance(requireContext());

        apiService.getReels(currentUser.getGender().name()).enqueue(new Callback<List<Reel>>() {
            @Override
            public void onResponse(Call<List<Reel>> call, Response<List<Reel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Reel> filtered = GenderGuard.filterReels(currentUser, response.body());
                    adapter.setReels(filtered);
                } else {
                    loadDemoReels(currentUser);
                }
            }

            @Override
            public void onFailure(Call<List<Reel>> call, Throwable t) {
                loadDemoReels(currentUser);
            }
        });
    }

    private void loadDemoReels(User currentUser) {
        List<Reel> demoList = new ArrayList<>();
        Gender gender = currentUser.getGender();

        if (gender == Gender.FEMALE) {
            User creator1 = new User("usr_f3", "esma", "Esma Hanım", Gender.FEMALE, null, "Eğitmen");
            demoList.add(new Reel("r1", creator1, null, "Ahlak ve edep üzerine kısa bir hatırlatma.", 145, "Bugün"));
        } else {
            User creator1 = new User("usr_m3", "omer", "Ömer Faruk", Gender.MALE, null, "Sohbetler");
            demoList.add(new Reel("r2", creator1, null, "Sünnete ittiba etmenin kalbe verdiği huzur.", 210, "Dün"));
        }

        List<Reel> safeReels = GenderGuard.filterReels(currentUser, demoList);
        adapter.setReels(safeReels);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
