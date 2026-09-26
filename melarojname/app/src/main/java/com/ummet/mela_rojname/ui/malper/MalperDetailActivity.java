// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.ui.malper;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ummet.mela_rojname.R;
import com.ummet.mela_rojname.databinding.ActivityMalperDetailBinding;

public class MalperDetailActivity extends AppCompatActivity {

    private ActivityMalperDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMalperDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String title = getIntent().getStringExtra("article_title");
        String category = getIntent().getStringExtra("article_category");
        String summary = getIntent().getStringExtra("article_summary");
        String content = getIntent().getStringExtra("article_content");
        String author = getIntent().getStringExtra("article_author");
        String date = getIntent().getStringExtra("article_date");
        String imageUrl = getIntent().getStringExtra("article_image");

        binding.toolbarDetail.setTitle(category != null ? category : "Mela İlim Portal");
        binding.toolbarDetail.setNavigationOnClickListener(v -> finish());

        binding.tvDetailCategory.setText(category != null ? category.toUpperCase() : "KURAN VE SÜNNET");
        binding.tvDetailTitle.setText(title != null ? title : "Kuran ve Sünnet İlimleri");
        binding.tvDetailMeta.setText((author != null ? author : "Mela İlim Kurulu") + " • " + (date != null ? date : "2026"));

        String fullBody = (content != null && !content.isEmpty()) ? content : summary;
        if (fullBody == null || fullBody.isEmpty()) {
            fullBody = "İslam dinini doğru kaynaklardan öğrenmek; Kuran-ı Kerim tefsiri, sahih hadis-i şerifler ve İslami fıkıh ışığında amel etmek Müslümanın asli vazifesidir. Rabbimiz bizleri ilimle amele muvaffak eylesin.";
        }
        final String articleBody = fullBody;
        binding.tvDetailContent.setText(articleBody);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this).load(imageUrl).placeholder(R.drawable.ic_launcher_background).into(binding.ivDetailImage);
        }

        binding.btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, title);
            shareIntent.putExtra(Intent.EXTRA_TEXT, title + "\n\n" + articleBody + "\n\nMela Malper İlim Merkezi");
            startActivity(Intent.createChooser(shareIntent, "Makaleyi Paylaş"));
        });
    }
}
