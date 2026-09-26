// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MalperResponse {
    @SerializedName("headlineTitle")
    private String headlineTitle;

    @SerializedName("headlineVideoUrl")
    private String headlineVideoUrl;

    @SerializedName("categories")
    private List<MalperCategory> categories;

    @SerializedName("featuredArticles")
    private List<MalperArticle> featuredArticles;

    @SerializedName("recentArticles")
    private List<MalperArticle> recentArticles;

    public MalperResponse() {}

    public MalperResponse(String headlineTitle, String headlineVideoUrl, List<MalperCategory> categories, List<MalperArticle> featuredArticles, List<MalperArticle> recentArticles) {
        this.headlineTitle = headlineTitle;
        this.headlineVideoUrl = headlineVideoUrl;
        this.categories = categories;
        this.featuredArticles = featuredArticles;
        this.recentArticles = recentArticles;
    }

    public String getHeadlineTitle() { return headlineTitle; }
    public String getHeadlineVideoUrl() { return headlineVideoUrl; }
    public List<MalperCategory> getCategories() { return categories; }
    public List<MalperArticle> getFeaturedArticles() { return featuredArticles; }
    public List<MalperArticle> getRecentArticles() { return recentArticles; }
}
