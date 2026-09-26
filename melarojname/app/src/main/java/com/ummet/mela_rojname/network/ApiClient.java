// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.network;

import android.content.Context;

import com.ummet.mela_rojname.data.SessionManager;
import com.ummet.mela_rojname.model.Gender;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://rasteqin.vercel.app/api/mobile/v1/";
    private static Retrofit retrofit = null;
    private static ApiService apiService = null;

    public static synchronized ApiService getInstance(Context context) {
        if (apiService == null) {
            Context appContext = context != null ? context.getApplicationContext() : context;
            SessionManager sessionManager = new SessionManager(appContext);

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            Interceptor authInterceptor = chain -> {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();

                String token = sessionManager.getToken();
                if (token != null && !token.isEmpty()) {
                    builder.header("Authorization", "Bearer " + token);
                }

                // Attach logged in user gender safely
                Gender gender = sessionManager.getUserGender();
                if (gender != null) {
                    builder.header("X-User-Gender", gender.name());
                } else {
                    builder.header("X-User-Gender", Gender.MALE.name());
                }

                return chain.proceed(builder.build());
            };

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }
}
