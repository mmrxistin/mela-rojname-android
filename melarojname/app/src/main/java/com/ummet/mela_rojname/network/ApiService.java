// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.network;

import com.ummet.mela_rojname.model.MalperArticle;
import com.ummet.mela_rojname.model.MalperResponse;
import com.ummet.mela_rojname.model.Message;
import com.ummet.mela_rojname.model.ModerationResult;
import com.ummet.mela_rojname.model.Post;
import com.ummet.mela_rojname.model.Reel;
import com.ummet.mela_rojname.model.Story;
import com.ummet.mela_rojname.model.User;
import com.ummet.mela_rojname.network.dto.AuthResponse;
import com.ummet.mela_rojname.network.dto.LoginRequest;
import com.ummet.mela_rojname.network.dto.MessageRequest;
import com.ummet.mela_rojname.network.dto.ModerationRequest;
import com.ummet.mela_rojname.network.dto.RegisterRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // Auth Endpoints
    @POST("auth/login")
    Call<AuthResponse> login(@Body LoginRequest request);

    @POST("auth/register")
    Call<AuthResponse> register(@Body RegisterRequest request);

    @GET("auth/me")
    Call<User> getCurrentUser();

    // Sosyal / Feed / Reels / Malper Endpoints
    @GET("sosyal/posts")
    Call<List<Post>> getPosts(@Query("gender") String gender);

    @POST("sosyal/posts")
    Call<Post> createPost(@Body Post post);

    @GET("sosyal/reels")
    Call<List<Reel>> getReels(@Query("gender") String gender);

    @POST("sosyal/reels")
    Call<Reel> createReel(@Body Reel reel);

    @GET("sosyal/stories")
    Call<List<Story>> getStories(@Query("gender") String gender);

    @POST("sosyal/stories")
    Call<Story> createStory(@Body Story story);

    @GET("sosyal/malper")
    Call<MalperResponse> getMalperContent();

    @GET("sosyal/malper/articles")
    Call<List<MalperArticle>> getMalperArticles(@Query("category") String category);

    // Etkilesim / Messages / Follow / Parastin AI Endpoints
    @GET("etkilesim/users")
    Call<List<User>> getUsers(@Query("gender") String gender);

    @POST("etkilesim/users/{id}/follow")
    Call<Void> followUser(@Path("id") String userId);

    @GET("etkilesim/messages/{recipientId}")
    Call<List<Message>> getMessages(@Path("recipientId") String recipientId);

    @POST("etkilesim/messages")
    Call<Message> sendMessage(@Body MessageRequest request);

    @POST("etkilesim/parastin/check")
    Call<ModerationResult> checkContentWithAi(@Body ModerationRequest request);
}
