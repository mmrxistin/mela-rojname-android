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

import com.ummet.mela_rojname.databinding.ItemMalperCategoryBinding;
import com.ummet.mela_rojname.model.MalperCategory;

import java.util.ArrayList;
import java.util.List;

public class MalperCategoryAdapter extends RecyclerView.Adapter<MalperCategoryAdapter.CategoryViewHolder> {

    public interface OnCategoryClickListener {
        void onCategoryClick(MalperCategory category);
    }

    private final List<MalperCategory> categories = new ArrayList<>();
    private final OnCategoryClickListener listener;

    public MalperCategoryAdapter(OnCategoryClickListener listener) {
        this.listener = listener;
    }

    public void setCategories(List<MalperCategory> newCategories) {
        categories.clear();
        if (newCategories != null) {
            categories.addAll(newCategories);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMalperCategoryBinding binding = ItemMalperCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(categories.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemMalperCategoryBinding binding;

        public CategoryViewHolder(ItemMalperCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MalperCategory category, OnCategoryClickListener listener) {
            binding.tvCategoryName.setText(category.getTitle());
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCategoryClick(category);
                }
            });
        }
    }
}
