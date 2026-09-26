// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.model;

import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("id")
    private String id;

    @SerializedName("author")
    private User author;

    @SerializedName("content")
    private String content;

    @SerializedName("mediaUrl")
    private String mediaUrl;

    @SerializedName("likesCount")
    private int likesCount;

    @SerializedName("commentsCount")
    private int commentsCount;

    @SerializedName("isLiked")
    private boolean isLiked;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("isApprovedByAi")
    private boolean isApprovedByAi;

    public Post() {}

    public Post(String id, User author, String content, String mediaUrl, int likesCount, int commentsCount, String createdAt) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.mediaUrl = mediaUrl;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
        this.createdAt = createdAt;
        this.isApprovedByAi = true;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getMediaUrl() { return mediaUrl; }
    public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }

    public int getLikesCount() { return likesCount; }
    public void setLikesCount(int likesCount) { this.likesCount = likesCount; }

    public int getCommentsCount() { return commentsCount; }
    public void setCommentsCount(int commentsCount) { this.commentsCount = commentsCount; }

    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public boolean isApprovedByAi() { return isApprovedByAi; }
    public void setApprovedByAi(boolean approvedByAi) { isApprovedByAi = approvedByAi; }
}
