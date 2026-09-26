package com.ummet.mela_rojname.network.dto;

import com.google.gson.annotations.SerializedName;

public class ModerationRequest {
    @SerializedName("contentType")
    private String contentType; // "POST", "REEL", "PROFILE", "BIO"

    @SerializedName("text")
    private String text;

    @SerializedName("mediaUrl")
    private String mediaUrl;

    public ModerationRequest(String contentType, String text, String mediaUrl) {
        this.contentType = contentType;
        this.text = text;
        this.mediaUrl = mediaUrl;
    }

    public String getContentType() { return contentType; }
    public String getText() { return text; }
    public String getMediaUrl() { return mediaUrl; }
}
