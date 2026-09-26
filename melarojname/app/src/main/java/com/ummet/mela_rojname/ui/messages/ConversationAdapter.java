package com.ummet.mela_rojname.ui.messages;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ummet.mela_rojname.R;
import com.ummet.mela_rojname.databinding.ItemConversationBinding;
import com.ummet.mela_rojname.model.Gender;
import com.ummet.mela_rojname.model.User;

import java.util.ArrayList;
import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> {

    public interface OnUserClickListener {
        void onUserClick(User user);
    }

    private final List<User> users = new ArrayList<>();
    private final OnUserClickListener listener;

    public ConversationAdapter(OnUserClickListener listener) {
        this.listener = listener;
    }

    public void setUsers(List<User> newUsers) {
        users.clear();
        if (newUsers != null) {
            users.addAll(newUsers);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemConversationBinding binding = ItemConversationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ConversationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        holder.bind(users.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class ConversationViewHolder extends RecyclerView.ViewHolder {
        private final ItemConversationBinding binding;

        public ConversationViewHolder(ItemConversationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(User user, OnUserClickListener listener) {
            binding.tvUserName.setText(user.getFullName());
            binding.tvLastMessage.setText(user.getBio() != null ? user.getBio() : "Esselamualeykum");

            if (user.getGender() == Gender.FEMALE) {
                binding.tvGenderBadge.setText("KADIN");
                binding.tvGenderBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.female_rose));
                binding.cardGenderBadge.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.female_rose_bg));
            } else {
                binding.tvGenderBadge.setText("ERKEK");
                binding.tvGenderBadge.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.male_navy));
                binding.cardGenderBadge.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.male_navy_bg));
            }

            if (user.getAvatarUrl() != null && !user.getAvatarUrl().isEmpty()) {
                Glide.with(itemView.getContext()).load(user.getAvatarUrl()).placeholder(R.drawable.ic_launcher_foreground).into(binding.ivUserAvatar);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onUserClick(user);
                }
            });
        }
    }
}
