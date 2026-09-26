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

    @POST("auth/login")
    Call<AuthResponse> login(@Body LoginRequest request);

    @POST("auth/register")
    Call<AuthResponse> register(@Body RegisterRequest request);

    @GET("users/me")
    Call<User> getCurrentUser();

    @GET("users")
    Call<List<User>> getUsers(@Query("gender") String gender);

    @GET("posts")
    Call<List<Post>> getPosts(@Query("gender") String gender);

    @POST("posts")
    Call<Post> createPost(@Body Post post);

    @GET("reels")
    Call<List<Reel>> getReels(@Query("gender") String gender);

    @POST("reels")
    Call<Reel> createReel(@Body Reel reel);

    @POST("parastin/check")
    Call<ModerationResult> checkContentWithAi(@Body ModerationRequest request);

    @GET("messages/{recipientId}")
    Call<List<Message>> getMessages(@Path("recipientId") String recipientId);

    @POST("messages")
    Call<Message> sendMessage(@Body MessageRequest request);

    @POST("users/{id}/follow")
    Call<Void> followUser(@Path("id") String userId);

    @GET("malper")
    Call<MalperResponse> getMalperContent();

    @GET("malper/articles")
    Call<List<MalperArticle>> getMalperArticles(@Query("category") String category);
}
