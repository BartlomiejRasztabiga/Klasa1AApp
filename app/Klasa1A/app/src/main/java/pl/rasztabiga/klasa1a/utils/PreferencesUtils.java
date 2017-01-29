package pl.rasztabiga.klasa1a.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import pl.rasztabiga.klasa1a.R;

public class PreferencesUtils {

    public static boolean getIsFirstRun(@NonNull Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean("isFirstRun", true);
    }

    public static String getApiKey(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String apiKey = preferences.getString(context.getString(R.string.apiKey_pref_key), "");
        return apiKey;
    }

    public static void saveApiKey(Context context, String apiKey) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(context.getResources().getString(R.string.apiKey_pref_key), apiKey).apply();
    }
}
