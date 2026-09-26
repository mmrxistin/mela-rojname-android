// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.ui.malper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ummet.mela_rojname.databinding.FragmentMalperBinding;
import com.ummet.mela_rojname.model.MalperArticle;
import com.ummet.mela_rojname.model.MalperCategory;
import com.ummet.mela_rojname.model.MalperResponse;
import com.ummet.mela_rojname.network.ApiClient;
import com.ummet.mela_rojname.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MalperFragment extends Fragment implements MalperCategoryAdapter.OnCategoryClickListener, MalperArticleAdapter.OnArticleClickListener {

    private FragmentMalperBinding binding;
    private MalperCategoryAdapter categoryAdapter;
    private MalperArticleAdapter articleAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMalperBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryAdapter = new MalperCategoryAdapter(this);
        articleAdapter = new MalperArticleAdapter(this);

        Context context = getContext();
        if (context == null) return;

        binding.rvCategories.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.rvCategories.setAdapter(categoryAdapter);

        binding.rvArticles.setLayoutManager(new LinearLayoutManager(context));
        binding.rvArticles.setAdapter(articleAdapter);

        binding.swipeRefresh.setOnRefreshListener(this::loadMalperData);

        loadMalperData();
    }

    private void loadMalperData() {
        if (binding == null || getContext() == null) return;

        binding.swipeRefresh.setRefreshing(true);
        ApiService apiService = ApiClient.getInstance(getContext());

        apiService.getMalperContent().enqueue(new Callback<MalperResponse>() {
            @Override
            public void onResponse(Call<MalperResponse> call, Response<MalperResponse> response) {
                if (binding == null || !isAdded()) return;
                binding.swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    MalperResponse data = response.body();
                    if (data.getCategories() != null) categoryAdapter.setCategories(data.getCategories());
                    if (data.getFeaturedArticles() != null) articleAdapter.setArticles(data.getFeaturedArticles());
                } else {
                    loadDemoMalperData();
                }
            }

            @Override
            public void onFailure(Call<MalperResponse> call, Throwable t) {
                if (binding == null || !isAdded()) return;
                binding.swipeRefresh.setRefreshing(false);
                loadDemoMalperData();
            }
        });
    }

    private void loadDemoMalperData() {
        if (binding == null || !isAdded()) return;

        List<MalperCategory> categories = new ArrayList<>();
        categories.add(new MalperCategory("c1", "Tüm İçerikler", "ALL", 12));
        categories.add(new MalperCategory("c2", "Kuran-ı Kerim", "KURAN", 8));
        categories.add(new MalperCategory("c3", "Hadis / Sünnet", "HADIS", 10));
        categories.add(new MalperCategory("c4", "Fıkıh / İlmihal", "FIKIH", 6));
        categories.add(new MalperCategory("c5", "Namaz / İbadet", "NAMAZ", 5));
        categories.add(new MalperCategory("c6", "Ahlak / Sohbetler", "AHLAK", 9));
        categories.add(new MalperCategory("c7", "Âlimler / Yazarlar", "ALIMLER", 4));
        categoryAdapter.setCategories(categories);

        List<MalperArticle> articles = new ArrayList<>();
        articles.add(new MalperArticle("a1", "Kuran-ı Kerim Tefsirinde Usul ve Ehemmiyet", "Kuran-ı Kerim",
                "Ayet-i kerimeleri doğru fehmetmek ve hayatımıza tatbik etmek için ashab-ı kiram ve müfessirlerin usulüne tabi olmak.",
                "İslam ilim geleneğinde Kuran-ı Kerim tefsiri en şerefli ilimlerden kabul edilmiştir. Kuran-ı Kerim'i anlamak sadece lafzi okumakla değil, nüzul sebepleri ve sünnet-i seniyye ışığında tefekkür etmekle mümkündür.",
                "Mela İlim Heyeti", null, "26 Eylül 2026", "5 dk okuma"));

        articles.add(new MalperArticle("a2", "Sünnet-i Seniyye'ye İttiba Etmenin Yolu", "Hadis / Sünnet",
                "Peygamber Efendimiz'in (s.a.v.) sünnetini günlük hayatımıza aktarmanın bereketi ve mümin şahsiyeti üzerindeki tesiri.",
                "Sünnet, Kuran-ı Kerim'in canlı tatbikatıdır. Yeme-içme adabından komşuluk ilişkilerine kadar Resulullah'ın izinden gitmek kalbe huzur, hayata bereket verir.",
                "Salih Hoca", null, "25 Eylül 2026", "4 dk okuma"));

        articles.add(new MalperArticle("a3", "Namazda Huşuu Yakalamak ve Kalbi İbadete Hazırlamak", "Namaz / İbadet",
                "Namazın ruhunu kavramak, abdestten secdemize kadar tam bir teslimiyetle fani dünyadan sıyrılmak.",
                "Namaz müminin miracıdır. Takva sahibi müminler namaza dururken Rabblerinin huzuruna çıktıklarının şuuruyla hareket ederler.",
                "Hafız Zeynep Hanım", null, "24 Eylül 2026", "6 dk okuma"));

        articleAdapter.setArticles(articles);
    }

    @Override
    public void onCategoryClick(MalperCategory category) {
        if (category == null || binding == null || !isAdded() || getContext() == null) return;

        if ("ALL".equals(category.getCode())) {
            loadMalperData();
            return;
        }

        binding.swipeRefresh.setRefreshing(true);
        ApiService apiService = ApiClient.getInstance(getContext());
        apiService.getMalperArticles(category.getCode()).enqueue(new Callback<List<MalperArticle>>() {
            @Override
            public void onResponse(Call<List<MalperArticle>> call, Response<List<MalperArticle>> response) {
                if (binding == null || !isAdded()) return;
                binding.swipeRefresh.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    articleAdapter.setArticles(response.body());
                } else {
                    loadDemoMalperData();
                }
            }

            @Override
            public void onFailure(Call<List<MalperArticle>> call, Throwable t) {
                if (binding == null || !isAdded()) return;
                binding.swipeRefresh.setRefreshing(false);
                loadDemoMalperData();
            }
        });
    }

    @Override
    public void onArticleClick(MalperArticle article) {
        if (article == null || getContext() == null) return;

        Intent intent = new Intent(getContext(), MalperDetailActivity.class);
        intent.putExtra("article_id", article.getId());
        intent.putExtra("article_title", article.getTitle());
        intent.putExtra("article_category", article.getCategory());
        intent.putExtra("article_summary", article.getSummary());
        intent.putExtra("article_content", article.getContent());
        intent.putExtra("article_author", article.getAuthor());
        intent.putExtra("article_date", article.getPublishDate());
        intent.putExtra("article_image", article.getImageUrl());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
