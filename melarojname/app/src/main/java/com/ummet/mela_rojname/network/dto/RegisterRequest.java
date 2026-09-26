// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.network.dto;

import com.google.gson.annotations.SerializedName;
import com.ummet.mela_rojname.model.Gender;

public class RegisterRequest {
    @SerializedName("username")
    private String username;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("gender")
    private Gender gender;

    public RegisterRequest(String username, String fullName, String email, String password, Gender gender) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Gender getGender() { return gender; }
}
