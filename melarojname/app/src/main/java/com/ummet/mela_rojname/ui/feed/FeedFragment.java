// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.ui.feed;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ummet.mela_rojname.data.SessionManager;
import com.ummet.mela_rojname.databinding.FragmentFeedBinding;
import com.ummet.mela_rojname.model.Gender;
import com.ummet.mela_rojname.model.Post;
import com.ummet.mela_rojname.model.User;
import com.ummet.mela_rojname.network.ApiClient;
import com.ummet.mela_rojname.network.ApiService;
import com.ummet.mela_rojname.security.GenderGuard;
import com.ummet.mela_rojname.security.ParastinModerationGuard;
import com.ummet.mela_rojname.ui.auth.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedFragment extends Fragment {

    private FragmentFeedBinding binding;
    private PostAdapter adapter;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFeedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager = new SessionManager(requireContext());
        adapter = new PostAdapter();

        binding.rvPosts.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvPosts.setAdapter(adapter);

        binding.swipeRefresh.setOnRefreshListener(this::loadPosts);
        binding.fabAddPost.setOnClickListener(v -> {
            if (!sessionManager.isLoggedIn()) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Giriş Yapılması Gerekiyor")
                        .setMessage("Gönderi paylaşabilmek için lütfen giriş yapın veya kayıt olun.")
                        .setPositiveButton("Giriş Yap", (dialog, which) -> startActivity(new Intent(requireContext(), LoginActivity.class)))
                        .setNegativeButton("İptal", null)
                        .show();
                return;
            }
            showCreatePostDialog();
        });

        loadPosts();
    }

    private void loadPosts() {
        binding.swipeRefresh.setRefreshing(true);
        User currentUser = sessionManager.getCurrentUser();
        ApiService apiService = ApiClient.getInstance(requireContext());

        apiService.getPosts(currentUser.getGender().name()).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                binding.swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    List<Post> filteredPosts = GenderGuard.filterPosts(currentUser, response.body());
                    adapter.setPosts(filteredPosts);
                } else {
                    loadDemoPosts(currentUser);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                binding.swipeRefresh.setRefreshing(false);
                loadDemoPosts(currentUser);
            }
        });
    }

    private void loadDemoPosts(User currentUser) {
        List<Post> demoList = new ArrayList<>();
        Gender userGender = currentUser.getGender();

        if (userGender == Gender.FEMALE) {
            User author1 = new User("usr_f1", "zeynep", "Zeynep Hanım", Gender.FEMALE, null, "Hafız & Eğitmen");
            User author2 = new User("usr_f2", "fatma", "Fatma Zehra", Gender.FEMALE, null, "İslami İlimler");
            demoList.add(new Post("p1", author1, "Elhamdülillah bugünkü tefsir dersimiz bitti. Birlik ve beraberliğimiz daim olsun.", null, 42, 8, "15 dk önce"));
            demoList.add(new Post("p2", author2, "Niyetlerimizi tazeleyerek hayırlı işlere niyet edelim inşallah.", null, 28, 4, "1 saat önce"));
        } else {
            User author1 = new User("usr_m1", "ahmed", "Ahmed Faruk", Gender.MALE, null, "Mela Üyesi");
            User author2 = new User("usr_m2", "hamza", "Hamza Bey", Gender.MALE, null, "Sünnet Talebesi");
            demoList.add(new Post("p3", author1, "Esselamualeykum ve rahmetullah. Cumanız mübarek olsun kıymetli kardeşlerim.", null, 56, 12, "20 dk önce"));
            demoList.add(new Post("p4", author2, "İlim öğrenmek her Müslümana farzdır. Gayretimiz daim olsun.", null, 34, 6, "2 saat önce"));
        }

        List<Post> safePosts = GenderGuard.filterPosts(currentUser, demoList);
        adapter.setPosts(safePosts);
    }

    private void showCreatePostDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Yeni Gönderi (Parastın AI Denetimli)");

        final EditText etInput = new EditText(requireContext());
        etInput.setHint("Gönderinizi yazınız...");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        etInput.setLayoutParams(lp);
        builder.setView(etInput);

        builder.setPositiveButton("Paylaş", (dialog, which) -> {
            String text = etInput.getText().toString().trim();
            if (!text.isEmpty()) {
                submitPostWithAiCheck(text);
            }
        });
        builder.setNegativeButton("İptal", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void submitPostWithAiCheck(String text) {
        Toast.makeText(requireContext(), "Parastın AI kontrolü yapılıyor...", Toast.LENGTH_SHORT).show();

        ParastinModerationGuard.checkContent(requireContext(), "POST", text, null, new ParastinModerationGuard.ModerationCallback() {
            @Override
            public void onApproved() {
                User currentUser = sessionManager.getCurrentUser();
                Post newPost = new Post("post_" + System.currentTimeMillis(), currentUser, text, null, 0, 0, "Şimdi");
                newPost.setApprovedByAi(true);

                Toast.makeText(requireContext(), "Parastın AI Onayladı! Gönderi yayınlandı.", Toast.LENGTH_SHORT).show();
                loadPosts();
            }

            @Override
            public void onRejected(String reason) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("İçerik Reddedildi")
                        .setMessage(reason)
                        .setPositiveButton("Anlaşıldı", null)
                        .show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
