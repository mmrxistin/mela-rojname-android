// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.model;

import com.google.gson.annotations.SerializedName;

public class MalperArticle {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("category")
    private String category;

    @SerializedName("summary")
    private String summary;

    @SerializedName("content")
    private String content;

    @SerializedName("author")
    private String author;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("publishDate")
    private String publishDate;

    @SerializedName("readTime")
    private String readTime;

    public MalperArticle() {}

    public MalperArticle(String id, String title, String category, String summary, String content, String author, String imageUrl, String publishDate, String readTime) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.summary = summary;
        this.content = content;
        this.author = author;
        this.imageUrl = imageUrl;
        this.publishDate = publishDate;
        this.readTime = readTime;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getSummary() { return summary; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public String getImageUrl() { return imageUrl; }
    public String getPublishDate() { return publishDate; }
    public String getReadTime() { return readTime; }
}
