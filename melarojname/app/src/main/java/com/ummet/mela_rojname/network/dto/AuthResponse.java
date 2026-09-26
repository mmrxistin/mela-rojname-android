package com.ummet.mela_rojname.network.dto;

import com.google.gson.annotations.SerializedName;
import com.ummet.mela_rojname.model.User;

public class AuthResponse {
    @SerializedName("token")
    private String token;

    @SerializedName("user")
    private User user;

    public AuthResponse() {}

    public AuthResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() { return token; }
    public User getUser() { return user; }
}
