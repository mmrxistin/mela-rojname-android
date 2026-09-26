// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.model;

import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("id")
    private String id;

    @SerializedName("sender")
    private User sender;

    @SerializedName("recipient")
    private User recipient;

    @SerializedName("text")
    private String text;

    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("isMine")
    private boolean isMine;

    public Message() {}

    public Message(String id, User sender, User recipient, String text, String timestamp, boolean isMine) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.text = text;
        this.timestamp = timestamp;
        this.isMine = isMine;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public User getSender() { return sender; }
    public void setSender(User sender) { this.sender = sender; }

    public User getRecipient() { return recipient; }
    public void setRecipient(User recipient) { this.recipient = recipient; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public boolean isMine() { return isMine; }
    public void setMine(boolean mine) { isMine = mine; }
}
