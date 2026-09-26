// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber
package com.ummet.mela_rojname;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;

/**
 * Mela Rojname — Next.js websitesinin mobil uygulaması.
 * Website içeriği WebView içinde açılır.
 */
public class MainActivity extends ComponentActivity {

    // Next.js websitesinin adresi.
    // Emülatörde local development için: http://10.0.2.2:3000
    // Yayındaki site için kendi Vercel adresini buraya yaz.
    private static final String SITE_URL = "http://10.0.2.2:3000";

    private WebView webView;
    private LinearLayout offlineView;
    private LinearLayout root;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.parseColor("#F5E8C5"));

        // --- WebView ---
        webView = new WebView(this);
        webView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(false);
        webView.setBackgroundColor(Color.parseColor("#F5E8C5"));
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                // Site içinde gezinme WebView içinde kalır.
                view.loadUrl(request.getUrl().toString());
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                root.setBackgroundColor(Color.WHITE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (request.isForMainFrame()) {
                    showOffline();
                }
            }
        });

        // --- Offline ekranı ---
        offlineView = createOfflineView();
        offlineView.setVisibility(View.GONE);

        root.addView(webView);
        root.addView(offlineView);
        setContentView(root);

        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        } else {
            loadSite();
        }
    }

    private void loadSite() {
        if (isOnline()) {
            offlineView.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl(SITE_URL);
        } else {
            showOffline();
        }
    }

    private void showOffline() {
        webView.setVisibility(View.GONE);
        offlineView.setVisibility(View.VISIBLE);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    private LinearLayout createOfflineView() {
        LinearLayout offline = new LinearLayout(this);
        offline.setOrientation(LinearLayout.VERTICAL);
        offline.setGravity(Gravity.CENTER);
        offline.setBackgroundColor(Color.parseColor("#F5E8C5"));
        offline.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        offline.setPadding(dp(24), dp(24), dp(24), dp(24));

        TextView icon = new TextView(this);
        icon.setText("✦");
        icon.setTextColor(Color.parseColor("#A96E29"));
        icon.setTextSize(40f);
        icon.setGravity(Gravity.CENTER);
        offline.addView(icon);

        TextView title = new TextView(this);
        title.setText("Bağlantı yok");
        title.setTextColor(Color.parseColor("#2A1A10"));
        title.setTextSize(22f);
        title.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, dp(12), 0, dp(6));
        offline.addView(title);

        TextView message = new TextView(this);
        message.setText("İnternet bağlantını kontrol et, inşaAllah tekrar dene.");
        message.setTextColor(Color.parseColor("#5F473A"));
        message.setTextSize(15f);
        message.setGravity(Gravity.CENTER);
        offline.addView(message);

        TextView hint = new TextView(this);
        hint.setText("Bağlantı gelince uygulamayı yeniden aç.");
        hint.setTextColor(Color.parseColor("#6E574D"));
        hint.setTextSize(13f);
        hint.setGravity(Gravity.CENTER);
        hint.setPadding(0, dp(16), 0, 0);
        offline.addView(hint);

        offline.setOnClickListener(v -> {
            if (isOnline()) {
                loadSite();
            }
        });
        return offline;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Offline ekrandaysa ve bağlantı geldiyse siteyi tekrar yükle.
        if (offlineView.getVisibility() == View.VISIBLE && isOnline()) {
            loadSite();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    // Geri tuşu: önce WebView geçmişinde gezin, en sonda uygulamadan çık.
    @Override
    public void onBackPressed() {
        if (webView.getVisibility() == View.VISIBLE && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
