// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.model;

import com.google.gson.annotations.SerializedName;

public class MalperCategory {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("code")
    private String code;

    @SerializedName("articleCount")
    private int articleCount;

    public MalperCategory() {}

    public MalperCategory(String id, String title, String code, int articleCount) {
        this.id = id;
        this.title = title;
        this.code = code;
        this.articleCount = articleCount;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getCode() { return code; }
    public int getArticleCount() { return articleCount; }
}
