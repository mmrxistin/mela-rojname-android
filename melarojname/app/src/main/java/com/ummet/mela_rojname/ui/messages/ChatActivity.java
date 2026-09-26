package com.ummet.mela_rojname.ui.messages;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ummet.mela_rojname.data.SessionManager;
import com.ummet.mela_rojname.databinding.ActivityChatBinding;
import com.ummet.mela_rojname.model.Gender;
import com.ummet.mela_rojname.model.Message;
import com.ummet.mela_rojname.model.User;
import com.ummet.mela_rojname.security.GenderGuard;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private ChatAdapter adapter;
    private SessionManager sessionManager;
    private User currentUser;
    private User targetUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);
        currentUser = sessionManager.getCurrentUser();

        String targetId = getIntent().getStringExtra("target_user_id");
        String targetName = getIntent().getStringExtra("target_user_name");
        String targetGenderStr = getIntent().getStringExtra("target_user_gender");

        Gender targetGender = Gender.fromString(targetGenderStr);
        targetUser = new User(targetId, targetName != null ? targetName.toLowerCase().replace(" ", "") : "user", targetName, targetGender, null, "");

        // Strict Same-Gender Enforcement
        if (!GenderGuard.canInteract(currentUser, targetUser)) {
            new AlertDialog.Builder(this)
                    .setTitle("Şeriat Engeli")
                    .setMessage("Şeriat kuralları gereği karşı cinsiyetteki kullanıcılarla konuşamazsınız.")
                    .setCancelable(false)
                    .setPositiveButton("Kapat", (dialog, which) -> finish())
                    .show();
            return;
        }

        setupViews(targetName);
        loadMessages();
    }

    private void setupViews(String targetName) {
        binding.chatToolbar.setTitle(targetName != null ? targetName : "Sohbet");
        binding.chatToolbar.setNavigationOnClickListener(v -> finish());

        adapter = new ChatAdapter();
        binding.rvChatMessages.setLayoutManager(new LinearLayoutManager(this));
        binding.rvChatMessages.setAdapter(adapter);

        binding.btnSend.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String text = binding.etMessageInput.getText().toString().trim();
        if (text.isEmpty()) return;

        if (!GenderGuard.canInteract(currentUser, targetUser)) {
            Toast.makeText(this, "Şeriat gereği karşı cinsiyete mesaj gönderilemez!", Toast.LENGTH_SHORT).show();
            return;
        }

        Message newMsg = new Message("msg_" + System.currentTimeMillis(), currentUser, targetUser, text, "Şimdi", true);
        adapter.addMessage(newMsg);
        binding.etMessageInput.setText("");
        binding.rvChatMessages.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    private void loadMessages() {
        List<Message> initialMessages = new ArrayList<>();
        initialMessages.add(new Message("m1", targetUser, currentUser, "Esselamualeykum, hayırlı günler.", "10:15", false));
        initialMessages.add(new Message("m2", currentUser, targetUser, "Ve aleykümselam ve rahmetullah.", "10:16", true));

        List<Message> safeMessages = GenderGuard.filterMessages(currentUser, initialMessages);
        adapter.setMessages(safeMessages);
    }
}
