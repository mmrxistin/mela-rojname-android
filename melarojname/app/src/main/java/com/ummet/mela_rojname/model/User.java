// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private String id;

    @SerializedName("username")
    private String username;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("email")
    private String email;

    @SerializedName("gender")
    private Gender gender;

    @SerializedName("avatarUrl")
    private String avatarUrl;

    @SerializedName("bio")
    private String bio;

    @SerializedName("followersCount")
    private int followersCount;

    @SerializedName("followingCount")
    private int followingCount;

    @SerializedName("isFollowing")
    private boolean isFollowing;

    @SerializedName("isVerifiedByAi")
    private boolean isVerifiedByAi;

    public User() {}

    public User(String id, String username, String fullName, Gender gender, String avatarUrl, String bio) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.gender = gender;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
        this.isVerifiedByAi = true;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Gender getGender() { return gender != null ? gender : Gender.MALE; }
    public void setGender(Gender gender) { this.gender = gender; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public int getFollowersCount() { return followersCount; }
    public void setFollowersCount(int followersCount) { this.followersCount = followersCount; }

    public int getFollowingCount() { return followingCount; }
    public void setFollowingCount(int followingCount) { this.followingCount = followingCount; }

    public boolean isFollowing() { return isFollowing; }
    public void setFollowing(boolean following) { isFollowing = following; }

    public boolean isVerifiedByAi() { return isVerifiedByAi; }
    public void setVerifiedByAi(boolean verifiedByAi) { isVerifiedByAi = verifiedByAi; }
}
