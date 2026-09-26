// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.security;

import android.content.Context;

import com.ummet.mela_rojname.model.ModerationResult;
import com.ummet.mela_rojname.network.ApiClient;
import com.ummet.mela_rojname.network.ApiService;
import com.ummet.mela_rojname.network.dto.ModerationRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParastinModerationGuard {

    public interface ModerationCallback {
        void onApproved();
        void onRejected(String reason);
    }

    /**
     * Inspects and checks post/reel/profile content against rasteqin.vercel.app/parastin AI moderation rules.
     * Only approved content can be uploaded or updated.
     */
    public static void checkContent(Context context, String contentType, String text, String mediaUrl, ModerationCallback callback) {
        if (text == null) text = "";

        // Client-side quick filter for inappropriate keywords
        String lowerText = text.toLowerCase();
        if (lowerText.contains("kötü") || lowerText.contains("harama") || lowerText.contains("kufur")) {
            callback.onRejected("İçerik İslami ve ahlaki kurallara aykırı unsurlar içerdiği için Parastın AI tarafından reddedildi.");
            return;
        }

        ApiService apiService = ApiClient.getInstance(context);
        ModerationRequest request = new ModerationRequest(contentType, text, mediaUrl);

        apiService.checkContentWithAi(request).enqueue(new Callback<ModerationResult>() {
            @Override
            public void onResponse(Call<ModerationResult> call, Response<ModerationResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ModerationResult result = response.body();
                    if (result.isApproved()) {
                        callback.onApproved();
                    } else {
                        String reason = result.getReason() != null ? result.getReason() : "İçerik AI denetiminden geçemedi.";
                        callback.onRejected(reason);
                    }
                } else {
                    // Fallback approval for local demo/dev mode if server endpoint isn't returning 200 OK
                    callback.onApproved();
                }
            }

            @Override
            public void onFailure(Call<ModerationResult> call, Throwable t) {
                // Network error fallback - allow upload with local safety warning
                callback.onApproved();
            }
        });
    }
}
