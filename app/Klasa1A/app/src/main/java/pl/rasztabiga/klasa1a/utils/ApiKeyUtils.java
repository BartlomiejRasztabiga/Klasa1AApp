package pl.rasztabiga.klasa1a.utils;


import android.content.Context;

public class ApiKeyUtils {

    private ApiKeyUtils() {}

    public static String getApiKey(Context context) {
        //TODO Uncomment alter
        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        //String apiKey = preferences.getString(context.getString(R.string.apiKey_pref_key), "");
        //return apiKey;
        return "tcknac";
    }
}
