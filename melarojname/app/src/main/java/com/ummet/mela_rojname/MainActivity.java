// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;
import com.ummet.mela_rojname.data.LanguageManager;
import com.ummet.mela_rojname.data.SessionManager;
import com.ummet.mela_rojname.databinding.ActivityMainBinding;
import com.ummet.mela_rojname.model.Gender;
import com.ummet.mela_rojname.model.User;
import com.ummet.mela_rojname.ui.auth.LoginActivity;
import com.ummet.mela_rojname.ui.feed.FeedFragment;
import com.ummet.mela_rojname.ui.malper.MalperFragment;
import com.ummet.mela_rojname.ui.messages.MessagesFragment;
import com.ummet.mela_rojname.ui.profile.ProfileFragment;
import com.ummet.mela_rojname.ui.reels.ReelsFragment;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private ActivityMainBinding binding;
    private SessionManager sessionManager;
    private LanguageManager languageManager;

    @Override
    protected void attachBaseContext(Context newBase) {
        LanguageManager langMgr = new LanguageManager(newBase);
        super.attachBaseContext(LanguageManager.applyLanguage(newBase, langMgr.getLanguage()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);
        languageManager = new LanguageManager(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupHeaderUI();

        binding.bottomNavigation.setOnItemSelectedListener(this);

        // Default tab on app launch: MALPER
        if (savedInstanceState == null) {
            binding.bottomNavigation.setSelectedItemId(R.id.navigation_malper);
        }

        binding.ivLanguage.setOnClickListener(v -> showLanguageSelectionDialog());
        binding.btnHeaderLogin.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));

        binding.ivLogout.setOnClickListener(v -> {
            sessionManager.logout();
            setupHeaderUI();
            binding.bottomNavigation.setSelectedItemId(R.id.navigation_malper);
        });
    }

    private void showLanguageSelectionDialog() {
        String[] languages = {"Kurdî (Kurmancî)", "Türkçe", "العربية (Arabic)", "English"};
        String[] langCodes = {LanguageManager.LANG_KURDISH, LanguageManager.LANG_TURKISH, LanguageManager.LANG_ARABIC, LanguageManager.LANG_ENGLISH};

        new AlertDialog.Builder(this)
                .setTitle(R.string.language_selection_title)
                .setItems(languages, (dialog, which) -> {
                    String selectedCode = langCodes[which];
                    languageManager.setLanguage(this, selectedCode);
                    recreate();
                })
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupHeaderUI();
    }

    private void setupHeaderUI() {
        if (binding == null) return;

        if (sessionManager.isLoggedIn()) {
            binding.btnHeaderLogin.setVisibility(View.GONE);
            binding.cardGenderBadge.setVisibility(View.VISIBLE);
            binding.ivLogout.setVisibility(View.VISIBLE);

            User user = sessionManager.getCurrentUser();
            if (user != null && user.getGender() == Gender.FEMALE) {
                binding.tvGenderBadge.setText(R.string.female_area);
                binding.tvGenderBadge.setTextColor(ContextCompat.getColor(this, R.color.female_rose));
                binding.cardGenderBadge.setCardBackgroundColor(ContextCompat.getColor(this, R.color.female_rose_bg));
            } else {
                binding.tvGenderBadge.setText(R.string.male_area);
                binding.tvGenderBadge.setTextColor(ContextCompat.getColor(this, R.color.male_navy));
                binding.cardGenderBadge.setCardBackgroundColor(ContextCompat.getColor(this, R.color.male_navy_bg));
            }
        } else {
            binding.btnHeaderLogin.setVisibility(View.VISIBLE);
            binding.cardGenderBadge.setVisibility(View.GONE);
            binding.ivLogout.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        int itemId = item.getItemId();

        if (itemId == R.id.navigation_feed) {
            selectedFragment = new FeedFragment();
        } else if (itemId == R.id.navigation_malper) {
            selectedFragment = new MalperFragment();
        } else if (itemId == R.id.navigation_reels) {
            selectedFragment = new ReelsFragment();
        } else if (itemId == R.id.navigation_messages) {
            selectedFragment = new MessagesFragment();
        } else if (itemId == R.id.navigation_profile) {
            selectedFragment = new ProfileFragment();
        }

        if (selectedFragment != null) {
            loadFragment(selectedFragment);
            return true;
        }
        return false;
    }

    private void loadFragment(Fragment fragment) {
        if (isFinishing() || isDestroyed()) return;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commitAllowingStateLoss();
    }
}
