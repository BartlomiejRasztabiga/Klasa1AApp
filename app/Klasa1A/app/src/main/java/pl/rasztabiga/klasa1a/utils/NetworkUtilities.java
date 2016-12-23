package pl.rasztabiga.klasa1a.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtilities {

    //private static final String SERVER_ADDR = "http://89.36.219.95:8007";
    private static final String SERVER_ADDR = "http://192.168.1.10:8007";

    private static final String DYZURNI_QUERY_URL = SERVER_ADDR + "/getdyzurni";
    private static final String VERSION_QUERY_URL = SERVER_ADDR + "/getversion";
    private static final String LUCKY_NUMBERS_QUERY_URL = SERVER_ADDR + "/getluckynumbers";
    private static final String EXAMS_QUERY_URL = SERVER_ADDR + "/getexams";

    public static String getDyzurni() {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(DYZURNI_QUERY_URL)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getExams() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(EXAMS_QUERY_URL)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getActualVersion() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(VERSION_QUERY_URL)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return Integer.valueOf(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static String getLuckyNumbers() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(LUCKY_NUMBERS_QUERY_URL)
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
