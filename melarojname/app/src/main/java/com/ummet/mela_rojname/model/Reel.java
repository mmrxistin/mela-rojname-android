// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.model;

import com.google.gson.annotations.SerializedName;

public class Reel {
    @SerializedName("id")
    private String id;

    @SerializedName("creator")
    private User creator;

    @SerializedName("videoUrl")
    private String videoUrl;

    @SerializedName("title")
    private String title;

    @SerializedName("likesCount")
    private int likesCount;

    @SerializedName("isLiked")
    private boolean isLiked;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("isApprovedByAi")
    private boolean isApprovedByAi;

    public Reel() {}

    public Reel(String id, User creator, String videoUrl, String title, int likesCount, String createdAt) {
        this.id = id;
        this.creator = creator;
        this.videoUrl = videoUrl;
        this.title = title;
        this.likesCount = likesCount;
        this.createdAt = createdAt;
        this.isApprovedByAi = true;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public User getCreator() { return creator; }
    public void setCreator(User creator) { this.creator = creator; }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getLikesCount() { return likesCount; }
    public void setLikesCount(int likesCount) { this.likesCount = likesCount; }

    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public boolean isApprovedByAi() { return isApprovedByAi; }
    public void setApprovedByAi(boolean approvedByAi) { isApprovedByAi = approvedByAi; }
}
