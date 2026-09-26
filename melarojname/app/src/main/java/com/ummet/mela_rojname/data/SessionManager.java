// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.ummet.mela_rojname.model.Gender;
import com.ummet.mela_rojname.model.User;

public class SessionManager {
    private static final String PREF_NAME = "mela_session_pref";
    private static final String KEY_TOKEN = "auth_token";
    private static final String KEY_USER = "current_user";
    private static final String KEY_LOGGED_IN = "is_logged_in";

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;
    private final Gson gson;

    public SessionManager(Context context) {
        this.pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.editor = pref.edit();
        this.gson = new Gson();
    }

    public void createSession(String token, User user) {
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_USER, gson.toJson(user));
        editor.putBoolean(KEY_LOGGED_IN, true);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_LOGGED_IN, false);
    }

    public String getToken() {
        return pref.getString(KEY_TOKEN, "");
    }

    public User getCurrentUser() {
        String userJson = pref.getString(KEY_USER, null);
        if (userJson != null) {
            try {
                return gson.fromJson(userJson, User.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Default dummy user for demo / offline fallback
        User defaultUser = new User();
        defaultUser.setId("user_default_1");
        defaultUser.setUsername("mela_user");
        defaultUser.setFullName("Mela Kullanıcısı");
        defaultUser.setGender(Gender.MALE);
        defaultUser.setBio("Elhamdülillah - Mela Rojname Üyesi");
        return defaultUser;
    }

    public Gender getUserGender() {
        User user = getCurrentUser();
        return user != null ? user.getGender() : Gender.MALE;
    }

    public void updateUser(User user) {
        editor.putString(KEY_USER, gson.toJson(user));
        editor.apply();
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
