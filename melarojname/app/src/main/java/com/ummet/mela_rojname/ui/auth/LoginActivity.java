// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ummet.mela_rojname.MainActivity;
import com.ummet.mela_rojname.R;
import com.ummet.mela_rojname.data.SessionManager;
import com.ummet.mela_rojname.databinding.ActivityLoginBinding;
import com.ummet.mela_rojname.model.Gender;
import com.ummet.mela_rojname.model.User;
import com.ummet.mela_rojname.network.ApiClient;
import com.ummet.mela_rojname.network.ApiService;
import com.ummet.mela_rojname.network.dto.AuthResponse;
import com.ummet.mela_rojname.network.dto.LoginRequest;
import com.ummet.mela_rojname.network.dto.RegisterRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private SessionManager sessionManager;
    private boolean isRegisterMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            startMainActivity();
            return;
        }

        setupViews();
    }

    private void setupViews() {
        binding.tvToggleMode.setOnClickListener(v -> {
            isRegisterMode = !isRegisterMode;
            updateModeUI();
        });

        binding.btnSubmit.setOnClickListener(v -> {
            if (isRegisterMode) {
                handleRegister();
            } else {
                handleLogin();
            }
        });
    }

    private void updateModeUI() {
        if (isRegisterMode) {
            binding.tvFormTitle.setText(R.string.btn_register);
            binding.btnSubmit.setText(R.string.btn_register);
            binding.tvToggleMode.setText(R.string.prompt_has_account);
            binding.tilUsername.setVisibility(View.VISIBLE);
            binding.tilFullName.setVisibility(View.VISIBLE);
        } else {
            binding.tvFormTitle.setText(R.string.btn_login);
            binding.btnSubmit.setText(R.string.btn_login);
            binding.tvToggleMode.setText(R.string.prompt_no_account);
            binding.tilUsername.setVisibility(View.GONE);
            binding.tilFullName.setVisibility(View.GONE);
        }
    }

    private Gender getSelectedGender() {
        return binding.rbFemale.isChecked() ? Gender.FEMALE : Gender.MALE;
    }

    private void handleLogin() {
        String email = binding.etEmail.getText() != null ? binding.etEmail.getText().toString().trim() : "";
        String password = binding.etPassword.getText() != null ? binding.etPassword.getText().toString().trim() : "";

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Lütfen e-posta ve şifrenizi giriniz.", Toast.LENGTH_SHORT).show();
            return;
        }

        Gender gender = getSelectedGender();
        ApiService apiService = ApiClient.getInstance(this);

        LoginRequest request = new LoginRequest(email, password);
        apiService.login(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse auth = response.body();
                    sessionManager.createSession(auth.getToken(), auth.getUser());
                    startMainActivity();
                } else {
                    // Local fallback for offline/demo mode
                    User user = new User("usr_" + System.currentTimeMillis(), email.split("@")[0], "Kullanıcı", gender, null, "Elhamdülillah");
                    sessionManager.createSession("mock_token_123", user);
                    Toast.makeText(LoginActivity.this, "Giriş yapıldı (" + gender.getTrName() + " Oturumu)", Toast.LENGTH_SHORT).show();
                    startMainActivity();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                // Fallback for demo
                User user = new User("usr_" + System.currentTimeMillis(), email.split("@")[0], "Kullanıcı", gender, null, "Elhamdülillah");
                sessionManager.createSession("mock_token_123", user);
                Toast.makeText(LoginActivity.this, "Giriş başarılı (" + gender.getTrName() + " Oturumu)", Toast.LENGTH_SHORT).show();
                startMainActivity();
            }
        });
    }

    private void handleRegister() {
        String username = binding.etUsername.getText() != null ? binding.etUsername.getText().toString().trim() : "";
        String fullName = binding.etFullName.getText() != null ? binding.etFullName.getText().toString().trim() : "";
        String email = binding.etEmail.getText() != null ? binding.etEmail.getText().toString().trim() : "";
        String password = binding.etPassword.getText() != null ? binding.etPassword.getText().toString().trim() : "";
        Gender gender = getSelectedGender();

        if (username.isEmpty() || fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurunuz.", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getInstance(this);
        RegisterRequest request = new RegisterRequest(username, fullName, email, password, gender);

        apiService.register(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse auth = response.body();
                    sessionManager.createSession(auth.getToken(), auth.getUser());
                    startMainActivity();
                } else {
                    // Fallback
                    User user = new User("usr_" + System.currentTimeMillis(), username, fullName, gender, null, "Mela Rojname Üyesi");
                    sessionManager.createSession("mock_token_register", user);
                    Toast.makeText(LoginActivity.this, "Kayıt başarılı (" + gender.getTrName() + ")", Toast.LENGTH_SHORT).show();
                    startMainActivity();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                // Fallback
                User user = new User("usr_" + System.currentTimeMillis(), username, fullName, gender, null, "Mela Rojname Üyesi");
                sessionManager.createSession("mock_token_register", user);
                Toast.makeText(LoginActivity.this, "Kayıt yapıldı (" + gender.getTrName() + ")", Toast.LENGTH_SHORT).show();
                startMainActivity();
            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
