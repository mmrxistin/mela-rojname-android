// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
// Suphan Allah i Azim ve Bihamdihi Vel Hamdu Lillah
// La ilahe ill Allah u
// Allah u Ekber

package com.ummet.mela_rojname.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import java.util.Locale;

public class LanguageManager {

    private static final String PREF_NAME = "mela_language_pref";
    private static final String KEY_LANGUAGE = "selected_language";

    public static final String LANG_KURDISH = "ku";
    public static final String LANG_TURKISH = "tr";
    public static final String LANG_ARABIC = "ar";
    public static final String LANG_ENGLISH = "en";

    private final SharedPreferences pref;

    public LanguageManager(Context context) {
        this.pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setLanguage(Context context, String langCode) {
        pref.edit().putString(KEY_LANGUAGE, langCode).apply();
        applyLanguage(context, langCode);
    }

    public String getLanguage() {
        return pref.getString(KEY_LANGUAGE, LANG_TURKISH);
    }

    public static Context applyLanguage(Context context, String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration config = new Configuration(resources.getConfiguration());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            return context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            resources.updateConfiguration(config, resources.getDisplayMetrics());
            return context;
        }
    }
}
