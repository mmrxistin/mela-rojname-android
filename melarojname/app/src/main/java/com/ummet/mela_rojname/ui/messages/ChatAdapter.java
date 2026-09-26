package com.ummet.mela_rojname.ui.messages;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ummet.mela_rojname.R;
import com.ummet.mela_rojname.databinding.ItemChatMessageBinding;
import com.ummet.mela_rojname.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private final List<Message> messages = new ArrayList<>();

    public void setMessages(List<Message> newMessages) {
        messages.clear();
        if (newMessages != null) {
            messages.addAll(newMessages);
        }
        notifyDataSetChanged();
    }

    public void addMessage(Message message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChatMessageBinding binding = ItemChatMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChatViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.bind(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        private final ItemChatMessageBinding binding;

        public ChatViewHolder(ItemChatMessageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Message message) {
            binding.tvMessageText.setText(message.getText());
            binding.tvMessageTime.setText(message.getTimestamp() != null ? message.getTimestamp() : "Şimdi");

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) binding.cardMessageContainer.getLayoutParams();
            if (message.isMine()) {
                params.gravity = Gravity.END;
                binding.cardMessageContainer.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.primary_emerald));
            } else {
                params.gravity = Gravity.START;
                binding.cardMessageContainer.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.text_secondary));
            }
            binding.cardMessageContainer.setLayoutParams(params);
        }
    }
}
