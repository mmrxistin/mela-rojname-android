// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.model;

import com.google.gson.annotations.SerializedName;

public class Story {
    @SerializedName("id")
    private String id;

    @SerializedName("user")
    private User user;

    @SerializedName("mediaUrl")
    private String mediaUrl;

    @SerializedName("caption")
    private String caption;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("isSeen")
    private boolean isSeen;

    @SerializedName("isApprovedByAi")
    private boolean isApprovedByAi;

    public Story() {}

    public Story(String id, User user, String mediaUrl, String caption, String createdAt) {
        this.id = id;
        this.user = user;
        this.mediaUrl = mediaUrl;
        this.caption = caption;
        this.createdAt = createdAt;
        this.isApprovedByAi = true;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getMediaUrl() { return mediaUrl; }
    public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }

    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public boolean isSeen() { return isSeen; }
    public void setSeen(boolean seen) { isSeen = seen; }

    public boolean isApprovedByAi() { return isApprovedByAi; }
    public void setApprovedByAi(boolean approvedByAi) { isApprovedByAi = approvedByAi; }
}
