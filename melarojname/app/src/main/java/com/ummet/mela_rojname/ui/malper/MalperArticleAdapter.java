// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.ui.malper;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ummet.mela_rojname.R;
import com.ummet.mela_rojname.databinding.ItemMalperArticleBinding;
import com.ummet.mela_rojname.model.MalperArticle;

import java.util.ArrayList;
import java.util.List;

public class MalperArticleAdapter extends RecyclerView.Adapter<MalperArticleAdapter.ArticleViewHolder> {

    public interface OnArticleClickListener {
        void onArticleClick(MalperArticle article);
    }

    private final List<MalperArticle> articles = new ArrayList<>();
    private final OnArticleClickListener listener;

    public MalperArticleAdapter(OnArticleClickListener listener) {
        this.listener = listener;
    }

    public void setArticles(List<MalperArticle> newArticles) {
        articles.clear();
        if (newArticles != null) {
            articles.addAll(newArticles);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMalperArticleBinding binding = ItemMalperArticleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ArticleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        holder.bind(articles.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        private final ItemMalperArticleBinding binding;

        public ArticleViewHolder(ItemMalperArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MalperArticle article, OnArticleClickListener listener) {
            binding.tvArticleCategory.setText(article.getCategory() != null ? article.getCategory().toUpperCase() : "İLİM");
            binding.tvArticleTitle.setText(article.getTitle());
            binding.tvArticleSummary.setText(article.getSummary());
            binding.tvReadTime.setText(article.getReadTime() != null ? article.getReadTime() : "3 dk okuma");
            binding.tvArticleAuthorDate.setText((article.getAuthor() != null ? article.getAuthor() : "Mela İlim Kurulu") + " • " + (article.getPublishDate() != null ? article.getPublishDate() : "Bugün"));

            if (article.getImageUrl() != null && !article.getImageUrl().isEmpty()) {
                Glide.with(itemView.getContext()).load(article.getImageUrl()).placeholder(R.drawable.ic_launcher_background).into(binding.ivArticleImage);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onArticleClick(article);
                }
            });
        }
    }
}
