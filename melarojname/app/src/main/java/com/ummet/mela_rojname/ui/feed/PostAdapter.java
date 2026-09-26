package com.ummet.mela_rojname.ui.feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ummet.mela_rojname.R;
import com.ummet.mela_rojname.databinding.ItemPostBinding;
import com.ummet.mela_rojname.model.Gender;
import com.ummet.mela_rojname.model.Post;
import com.ummet.mela_rojname.model.User;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private final List<Post> posts = new ArrayList<>();

    public void setPosts(List<Post> newPosts) {
        posts.clear();
        if (newPosts != null) {
            posts.addAll(newPosts);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        private final ItemPostBinding binding;

        public PostViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Post post) {
            User author = post.getAuthor();
            if (author != null) {
                binding.tvAuthorName.setText(author.getFullName());
                binding.tvUsernameTime.setText("@" + author.getUsername() + " • " + (post.getCreatedAt() != null ? post.getCreatedAt() : "Şimdi"));

                if (author.getGender() == Gender.FEMALE) {
                    binding.tvGenderTag.setText("KADIN");
                    binding.tvGenderTag.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.female_rose));
                    binding.cardGenderTag.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.female_rose_bg));
                } else {
                    binding.tvGenderTag.setText("ERKEK");
                    binding.tvGenderTag.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.male_navy));
                    binding.cardGenderTag.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.male_navy_bg));
                }

                if (author.getAvatarUrl() != null && !author.getAvatarUrl().isEmpty()) {
                    Glide.with(itemView.getContext()).load(author.getAvatarUrl()).placeholder(R.drawable.ic_launcher_foreground).into(binding.ivAvatar);
                }
            }

            binding.tvContent.setText(post.getContent());
            binding.tvLikesCount.setText(String.valueOf(post.getLikesCount()));
            binding.tvCommentsCount.setText(String.valueOf(post.getCommentsCount()));

            if (post.getMediaUrl() != null && !post.getMediaUrl().isEmpty()) {
                binding.ivMedia.setVisibility(View.VISIBLE);
                Glide.with(itemView.getContext()).load(post.getMediaUrl()).into(binding.ivMedia);
            } else {
                binding.ivMedia.setVisibility(View.GONE);
            }

            // AI Parastin Badge
            binding.ivAiBadge.setVisibility(post.isApprovedByAi() ? View.VISIBLE : View.GONE);
        }
    }
}
