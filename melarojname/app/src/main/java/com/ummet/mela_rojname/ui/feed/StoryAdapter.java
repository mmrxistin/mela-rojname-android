// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.ui.feed;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ummet.mela_rojname.R;
import com.ummet.mela_rojname.databinding.ItemStoryAvatarBinding;
import com.ummet.mela_rojname.model.Gender;
import com.ummet.mela_rojname.model.Story;
import com.ummet.mela_rojname.model.User;

import java.util.ArrayList;
import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {

    public interface OnStoryClickListener {
        void onStoryClick(Story story);
        void onAddStoryClick();
    }

    private final List<Story> stories = new ArrayList<>();
    private final OnStoryClickListener listener;

    public StoryAdapter(OnStoryClickListener listener) {
        this.listener = listener;
    }

    public void setStories(List<Story> newStories) {
        stories.clear();
        if (newStories != null) {
            stories.addAll(newStories);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStoryAvatarBinding binding = ItemStoryAvatarBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        holder.bind(stories.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    static class StoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemStoryAvatarBinding binding;

        public StoryViewHolder(ItemStoryAvatarBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Story story, OnStoryClickListener listener) {
            User user = story.getUser();
            if (user != null) {
                binding.tvStoryUsername.setText(user.getUsername() != null ? user.getUsername() : "Kullanıcı");

                int ringColor = user.getGender() == Gender.FEMALE ? R.color.female_rose : R.color.primary_emerald;
                binding.cardStoryRing.setStrokeColor(ContextCompat.getColor(itemView.getContext(), ringColor));

                if (user.getAvatarUrl() != null && !user.getAvatarUrl().isEmpty()) {
                    Glide.with(itemView.getContext()).load(user.getAvatarUrl()).placeholder(R.drawable.ic_launcher_foreground).into(binding.ivStoryAvatar);
                }
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onStoryClick(story);
                }
            });
        }
    }
}
