package pl.rasztabiga.klasa1a.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtilities {

    private static final String QUERY_URL = "http://89.36.219.95:8007/dyzurni";

    public static String getDyzurni() {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(QUERY_URL)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
