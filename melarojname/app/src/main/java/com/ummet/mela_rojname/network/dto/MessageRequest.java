package com.ummet.mela_rojname.network.dto;

import com.google.gson.annotations.SerializedName;

public class MessageRequest {
    @SerializedName("recipientId")
    private String recipientId;

    @SerializedName("text")
    private String text;

    public MessageRequest(String recipientId, String text) {
        this.recipientId = recipientId;
        this.text = text;
    }

    public String getRecipientId() { return recipientId; }
    public String getText() { return text; }
}
