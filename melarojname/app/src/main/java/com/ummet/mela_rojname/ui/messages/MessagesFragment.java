// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.ui.messages;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ummet.mela_rojname.data.SessionManager;
import com.ummet.mela_rojname.databinding.FragmentMessagesBinding;
import com.ummet.mela_rojname.model.Gender;
import com.ummet.mela_rojname.model.User;
import com.ummet.mela_rojname.network.ApiClient;
import com.ummet.mela_rojname.network.ApiService;
import com.ummet.mela_rojname.security.GenderGuard;
import com.ummet.mela_rojname.ui.auth.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesFragment extends Fragment implements ConversationAdapter.OnUserClickListener {

    private FragmentMessagesBinding binding;
    private ConversationAdapter adapter;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMessagesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager = new SessionManager(requireContext());
        adapter = new ConversationAdapter(this);

        binding.rvConversations.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvConversations.setAdapter(adapter);

        loadUsers();
    }

    private void loadUsers() {
        User currentUser = sessionManager.getCurrentUser();
        ApiService apiService = ApiClient.getInstance(requireContext());

        apiService.getUsers(currentUser.getGender().name()).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> safeUsers = GenderGuard.filterUsers(currentUser, response.body());
                    adapter.setUsers(safeUsers);
                } else {
                    loadDemoUsers(currentUser);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                loadDemoUsers(currentUser);
            }
        });
    }

    private void loadDemoUsers(User currentUser) {
        List<User> demoList = new ArrayList<>();
        Gender userGender = currentUser.getGender();

        if (userGender == Gender.FEMALE) {
            demoList.add(new User("usr_f1", "meryem", "Meryem Hanım", Gender.FEMALE, null, "Esselamualeykum, ders programı hazır mı?"));
            demoList.add(new User("usr_f2", "ayse", "Ayşe Nur", Gender.FEMALE, null, "Hayırlı günler dilerim."));
        } else {
            demoList.add(new User("usr_m1", "bilal", "Bilal Kardeş", Gender.MALE, null, "Ve aleykümselam, saat 20:00\'da buluşalım."));
            demoList.add(new User("usr_m2", "salih", "Salih Hoca", Gender.MALE, null, "Kitap listesini gönderiyorum."));
        }

        List<User> safeUsers = GenderGuard.filterUsers(currentUser, demoList);
        adapter.setUsers(safeUsers);
    }

    @Override
    public void onUserClick(User targetUser) {
        if (!sessionManager.isLoggedIn()) {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Giriş Yapılması Gerekiyor")
                    .setMessage("Mesajlaşabilmek için lütfen önce giriş yapın.")
                    .setPositiveButton("Giriş Yap", (dialog, which) -> startActivity(new Intent(requireContext(), LoginActivity.class)))
                    .setNegativeButton("İptal", null)
                    .show();
            return;
        }

        User currentUser = sessionManager.getCurrentUser();

        // Strict Gender Guard validation before opening chat
        if (!GenderGuard.canInteract(currentUser, targetUser)) {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Şeriat Engeli")
                    .setMessage("Şeriat kuralları gereği karşı cinsiyetteki kullanıcılarla iletişim kuramazsınız.")
                    .setPositiveButton("Tamam", null)
                    .show();
            return;
        }

        Intent intent = new Intent(requireContext(), ChatActivity.class);
        intent.putExtra("target_user_id", targetUser.getId());
        intent.putExtra("target_user_name", targetUser.getFullName());
        intent.putExtra("target_user_gender", targetUser.getGender().name());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
