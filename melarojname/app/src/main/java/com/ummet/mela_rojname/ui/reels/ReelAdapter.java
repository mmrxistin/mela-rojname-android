package com.ummet.mela_rojname.ui.reels;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ummet.mela_rojname.R;
import com.ummet.mela_rojname.databinding.ItemReelBinding;
import com.ummet.mela_rojname.model.Reel;
import com.ummet.mela_rojname.model.User;

import java.util.ArrayList;
import java.util.List;

public class ReelAdapter extends RecyclerView.Adapter<ReelAdapter.ReelViewHolder> {

    private final List<Reel> reels = new ArrayList<>();

    public void setReels(List<Reel> newReels) {
        reels.clear();
        if (newReels != null) {
            reels.addAll(newReels);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReelBinding binding = ItemReelBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ReelViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReelViewHolder holder, int position) {
        holder.bind(reels.get(position));
    }

    @Override
    public int getItemCount() {
        return reels.size();
    }

    static class ReelViewHolder extends RecyclerView.ViewHolder {
        private final ItemReelBinding binding;

        public ReelViewHolder(ItemReelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Reel reel) {
            User creator = reel.getCreator();
            if (creator != null) {
                binding.tvReelCreator.setText(creator.getFullName());
            }

            binding.tvReelTitle.setText(reel.getTitle());
            binding.tvReelLikes.setText(String.valueOf(reel.getLikesCount()));

            if (reel.getVideoUrl() != null && !reel.getVideoUrl().isEmpty()) {
                Glide.with(itemView.getContext()).load(reel.getVideoUrl()).placeholder(R.drawable.ic_launcher_background).into(binding.ivReelThumbnail);
            }
        }
    }
}
