package pl.rasztabiga.klasa1a.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.rasztabiga.klasa1a.RequestException;

public class NetworkUtilities {

    //private static final String SERVER_ADDR = "http://89.36.219.95:8007";
    private static final String SERVER_ADDR = "http://192.168.1.10:8007";

    private static final String DYZURNI_QUERY_URL = SERVER_ADDR + "/getdyzurni";
    private static final String VERSION_QUERY_URL = SERVER_ADDR + "/getversion";
    private static final String LUCKY_NUMBERS_QUERY_URL = SERVER_ADDR + "/getluckynumbers";
    private static final String EXAMS_QUERY_URL = SERVER_ADDR + "/getexams";
    private static final String CHECK_API_KEY_URL = SERVER_ADDR + "/checkApiKey";




    public static String getDyzurni(String apiKey) throws RequestException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(DYZURNI_QUERY_URL + "?apiKey=" + apiKey)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 500 || response.code() == 404 || response.code() == 401) {
                throw new RequestException();
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getExams(String apiKey) throws RequestException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(EXAMS_QUERY_URL + "?apiKey=" + apiKey)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 500 || response.code() == 404 || response.code() == 401) {
                throw new RequestException();
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO Add catching error codes

        return null;
    }

    public static int getActualVersion(String apiKey) throws RequestException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(VERSION_QUERY_URL + "?apiKey=" + apiKey)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 500 || response.code() == 404 || response.code() == 401) {
                throw new RequestException();
            }
            return Integer.valueOf(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static String getLuckyNumbers(String apiKey) throws RequestException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(LUCKY_NUMBERS_QUERY_URL + "?apiKey=" + apiKey)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 500 || response.code() == 404 || response.code() == 401) {
                throw new RequestException();
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String checkApikey(String apiKey) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(CHECK_API_KEY_URL + "?apiKey=" + apiKey)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if(response.code() == 200) {
                return null;
            } else if (response.code() == 500 || response.code() == 404 || response.code() == 401) {
                return "Nie znaleziono takiego klucza";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
