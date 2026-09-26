// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.ummet.mela_rojname.R;
import com.ummet.mela_rojname.data.SessionManager;
import com.ummet.mela_rojname.databinding.FragmentProfileBinding;
import com.ummet.mela_rojname.model.Gender;
import com.ummet.mela_rojname.model.User;
import com.ummet.mela_rojname.ui.auth.LoginActivity;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager = new SessionManager(requireContext());
        setupProfileUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupProfileUI();
    }

    private void setupProfileUI() {
        if (!sessionManager.isLoggedIn()) {
            binding.tvProfileName.setText("Misafir Ziyaretçi");
            binding.tvProfileUsername.setText("@misafir");
            binding.tvProfileBio.setText("Giriş yapmadınız. Mela Malper içeriklerini serbestçe inceleyebilirsiniz.");
            binding.tvProfileGender.setText("GİRİŞ YAPILMADI");
            binding.tvProfileGender.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary));
            binding.cardProfileGender.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.divider_color));

            binding.btnLogout.setText("Giriş Yap veya Üye Ol");
            binding.btnLogout.setOnClickListener(v -> startActivity(new Intent(requireContext(), LoginActivity.class)));
            return;
        }

        User user = sessionManager.getCurrentUser();
        if (user != null) {
            binding.tvProfileName.setText(user.getFullName());
            binding.tvProfileUsername.setText("@" + user.getUsername());
            binding.tvProfileBio.setText(user.getBio() != null ? user.getBio() : "Mela Rojname Üyesi");

            if (user.getGender() == Gender.FEMALE) {
                binding.tvProfileGender.setText("KADIN ALANI KULLANICISI");
                binding.tvProfileGender.setTextColor(ContextCompat.getColor(requireContext(), R.color.female_rose));
                binding.cardProfileGender.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.female_rose_bg));
            } else {
                binding.tvProfileGender.setText("ERKEK ALANI KULLANICISI");
                binding.tvProfileGender.setTextColor(ContextCompat.getColor(requireContext(), R.color.male_navy));
                binding.cardProfileGender.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.male_navy_bg));
            }

            if (user.getAvatarUrl() != null && !user.getAvatarUrl().isEmpty()) {
                Glide.with(requireContext()).load(user.getAvatarUrl()).placeholder(R.drawable.ic_launcher_foreground).into(binding.ivProfileAvatar);
            }
        }

        binding.btnLogout.setText("Oturumu Kapat");
        binding.btnLogout.setOnClickListener(v -> {
            sessionManager.logout();
            setupProfileUI();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
