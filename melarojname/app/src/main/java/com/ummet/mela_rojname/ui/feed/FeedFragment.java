// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.ui.feed;

import android.app.AlertDialog;
import android.content.Context;
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
import com.ummet.mela_rojname.model.Story;
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

public class FeedFragment extends Fragment implements StoryAdapter.OnStoryClickListener {

    private FragmentFeedBinding binding;
    private PostAdapter postAdapter;
    private StoryAdapter storyAdapter;
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

        Context context = getContext();
        if (context == null) return;

        sessionManager = new SessionManager(context);
        postAdapter = new PostAdapter();
        storyAdapter = new StoryAdapter(this);

        binding.rvStories.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.rvStories.setAdapter(storyAdapter);

        binding.rvPosts.setLayoutManager(new LinearLayoutManager(context));
        binding.rvPosts.setAdapter(postAdapter);

        binding.swipeRefresh.setOnRefreshListener(this::loadData);
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

        loadData();
    }

    private void loadData() {
        loadStories();
        loadPosts();
    }

    private void loadStories() {
        if (binding == null || getContext() == null) return;

        User currentUser = sessionManager.getCurrentUser();
        ApiService apiService = ApiClient.getInstance(getContext());

        apiService.getStories(currentUser.getGender().name()).enqueue(new Callback<List<Story>>() {
            @Override
            public void onResponse(Call<List<Story>> call, Response<List<Story>> response) {
                if (binding == null || !isAdded()) return;
                if (response.isSuccessful() && response.body() != null) {
                    List<Story> safeStories = GenderGuard.filterStories(currentUser, response.body());
                    storyAdapter.setStories(safeStories);
                } else {
                    loadDemoStories(currentUser);
                }
            }

            @Override
            public void onFailure(Call<List<Story>> call, Throwable t) {
                if (binding == null || !isAdded()) return;
                loadDemoStories(currentUser);
            }
        });
    }

    private void loadDemoStories(User currentUser) {
        if (binding == null || !isAdded()) return;

        List<Story> demoStories = new ArrayList<>();
        Gender gender = currentUser.getGender();

        if (gender == Gender.FEMALE) {
            User u1 = new User("s1", "meryem", "Meryem", Gender.FEMALE, null, "");
            User u2 = new User("s2", "fatma", "Fatma", Gender.FEMALE, null, "");
            demoStories.add(new Story("st1", u1, null, "Bugünün ayet meali...", "10 dk"));
            demoStories.add(new Story("st2", u2, null, "Tefsir sohbeti duyurusu", "1 saat"));
        } else {
            User u1 = new User("s3", "ahmed", "Ahmed", Gender.MALE, null, "");
            User u2 = new User("s4", "hamza", "Hamza", Gender.MALE, null, "");
            demoStories.add(new Story("st3", u1, null, "Cuma sohbeti hatırası", "20 dk"));
            demoStories.add(new Story("st4", u2, null, "Kuran tilaveti", "2 saat"));
        }

        List<Story> safeStories = GenderGuard.filterStories(currentUser, demoStories);
        storyAdapter.setStories(safeStories);
    }

    private void loadPosts() {
        if (binding == null || getContext() == null) return;

        binding.swipeRefresh.setRefreshing(true);
        User currentUser = sessionManager.getCurrentUser();
        ApiService apiService = ApiClient.getInstance(getContext());

        apiService.getPosts(currentUser.getGender().name()).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (binding == null || !isAdded()) return;
                binding.swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    List<Post> filteredPosts = GenderGuard.filterPosts(currentUser, response.body());
                    postAdapter.setPosts(filteredPosts);
                } else {
                    loadDemoPosts(currentUser);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                if (binding == null || !isAdded()) return;
                binding.swipeRefresh.setRefreshing(false);
                loadDemoPosts(currentUser);
            }
        });
    }

    private void loadDemoPosts(User currentUser) {
        if (binding == null || !isAdded()) return;

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
        postAdapter.setPosts(safePosts);
    }

    @Override
    public void onStoryClick(Story story) {
        if (story == null || story.getUser() == null) return;
        new AlertDialog.Builder(requireContext())
                .setTitle(story.getUser().getFullName() + " - Hikaye")
                .setMessage(story.getCaption() != null ? story.getCaption() : "Hikaye içeriği")
                .setPositiveButton("Kapat", null)
                .show();
    }

    @Override
    public void onAddStoryClick() {
        // Add story option
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
