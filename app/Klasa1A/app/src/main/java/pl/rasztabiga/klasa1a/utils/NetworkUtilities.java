package pl.rasztabiga.klasa1a.utils;

import com.google.firebase.crash.FirebaseCrash;

import java.io.IOException;
import java.net.ConnectException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.rasztabiga.klasa1a.Injection;
import pl.rasztabiga.klasa1a.RequestException;

public class NetworkUtilities {

    //private static final String SERVER_ADDR = "http://89.36.219.95:8007";
    //private static final String SERVER_ADDR = "http://192.168.1.24:8007";
    //private static final String SERVER_ADDR = "http://94.177.229.18:8007";
    private static final String SERVER_ADDR = Injection.provideServerAddress();

    private static final String DYZURNI_QUERY_URL = SERVER_ADDR + "/getdyzurni";
    private static final String ON_DUTIES_QUERY_URL = SERVER_ADDR + "/getOnDuties";
    private static final String VERSION_QUERY_URL = SERVER_ADDR + "/getversion";
    private static final String LUCKY_NUMBERS_QUERY_URL = SERVER_ADDR + "/getluckynumbers";
    private static final String EXAMS_QUERY_URL = SERVER_ADDR + "/getexams";
    private static final String CHECK_API_KEY_URL = SERVER_ADDR + "/checkApiKey";
    private static final String GETASSOCIATEDIMAGESLIST = SERVER_ADDR + "/getAssociatedImagesList";
    // is changingRoomOpen feature
    private static final String GETCHANGINGROOM_QUERY_URL = SERVER_ADDR + "/getchangingroomstatus";
    private static final String SETCHANGINGROOM_QUERY_URL = SERVER_ADDR + "/setchangingroomstatus";
    private static final String DOOR_QUERY_URL = SERVER_ADDR + "/getdoorstatus";
    private static final String SETDOOR_QUERY_URL = SERVER_ADDR + "/setdoorstatus";

    private static final int INTERNAL_SERVER_ERROR_CODE = 500;
    private static final int NOT_FOUND_CODE = 404;
    private static final int FORBIDDEN_CODE = 401;


    private static final String TAG = NetworkUtilities.class.getName();


    public static String getChangingRoomStatus(String apiKey) throws RequestException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(GETCHANGINGROOM_QUERY_URL + "?apiKey=" + apiKey)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 500 || response.code() == 404 || response.code() == 401) {
                throw new RequestException();
            }
            return response.body().string();
        } catch (ConnectException e) {
            //ignore
        } catch (IOException e) {
            FirebaseCrash.report(e);
            e.printStackTrace();
        }

        return null;
    }

    public static void setChangingRoomStatus(String apiKey, int changingRoomStatus) throws RequestException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(SETCHANGINGROOM_QUERY_URL + "?apiKey=" + apiKey + "&changingRoomStatus=" + changingRoomStatus)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 500 || response.code() == 404 || response.code() == 401) {
                throw new RequestException();
            }
        } catch (ConnectException e) {
            //ignore
        } catch (IOException e) {
            FirebaseCrash.report(e);
            e.printStackTrace();
        }
    }

    public static String getDoorStatus(String apiKey) throws RequestException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(DOOR_QUERY_URL + "?apiKey=" + apiKey)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 500 || response.code() == 404 || response.code() == 401) {
                throw new RequestException();
            }
            return response.body().string();
        } catch (ConnectException e) {
            //ignore
        } catch (IOException e) {
            FirebaseCrash.report(e);
            e.printStackTrace();
        }

        return null;
    }

    public static void setDoorStatus(String apiKey, int doorStatus) throws RequestException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(SETDOOR_QUERY_URL + "?apiKey=" + apiKey + "&doorStatus=" + doorStatus)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 500 || response.code() == 404 || response.code() == 401) {
                throw new RequestException();
            }
        } catch (ConnectException e) {
            //ignore
        } catch (IOException e) {
            FirebaseCrash.report(e);
            e.printStackTrace();
        }
    }


    public static String getDyzurni(String apiKey) throws RequestException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(DYZURNI_QUERY_URL + "?apiKey=" + apiKey)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() == INTERNAL_SERVER_ERROR_CODE || response.code() == NOT_FOUND_CODE || response.code() == FORBIDDEN_CODE) {
                throw new RequestException();
            }
            return response.body().string();
        } catch (ConnectException e) {
            //ignore
        } catch (IOException e) {
            FirebaseCrash.report(e);
            e.printStackTrace();
        }

        return null;
    }

    public static String getOnDuties(String apiKey) throws RequestException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(ON_DUTIES_QUERY_URL + "?apiKey=" + apiKey)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() == INTERNAL_SERVER_ERROR_CODE || response.code() == NOT_FOUND_CODE || response.code() == FORBIDDEN_CODE) {
                throw new RequestException();
            }
            return response.body().string();
        } catch (ConnectException e) {
            //ignore
        } catch (IOException e) {
            FirebaseCrash.report(e);
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
            if (response.code() == INTERNAL_SERVER_ERROR_CODE || response.code() == NOT_FOUND_CODE || response.code() == FORBIDDEN_CODE) {
                throw new RequestException();
            }
            return response.body().string();
        } catch (ConnectException e) {
            //ignore
        } catch (IOException e) {
            FirebaseCrash.report(e);
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
            if (response.code() == INTERNAL_SERVER_ERROR_CODE || response.code() == NOT_FOUND_CODE || response.code() == FORBIDDEN_CODE) {
                throw new RequestException();
            }
            //return Integer.valueOf(response.body().string());
            return Integer.parseInt(response.body().string());
        } catch (ConnectException e) {
            //ignore
        } catch (IOException e) {
            FirebaseCrash.report(e);
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
            if (response.code() == INTERNAL_SERVER_ERROR_CODE || response.code() == NOT_FOUND_CODE || response.code() == FORBIDDEN_CODE) {
                throw new RequestException();
            }
            return response.body().string();
        } catch (ConnectException e) {
            //ignore
        } catch (IOException e) {
            FirebaseCrash.report(e);
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
            if (response.code() == 200) {
                return null;
            } else if (response.code() == INTERNAL_SERVER_ERROR_CODE || response.code() == NOT_FOUND_CODE || response.code() == FORBIDDEN_CODE) {
                return "Nie znaleziono takiego klucza";
            }
        } catch (ConnectException e) {
            //ignore
        } catch (IOException e) {
            FirebaseCrash.report(e);
            e.printStackTrace();
        }

        return null;
    }

    public static boolean validateApiKey(String apiKey) throws RequestException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(CHECK_API_KEY_URL + "?apiKey=" + apiKey)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                return true;
            } else if (response.code() == INTERNAL_SERVER_ERROR_CODE || response.code() == NOT_FOUND_CODE || response.code() == FORBIDDEN_CODE) {
                throw new RequestException("ApiKey not found");
            }
        } catch (IOException e) {
            FirebaseCrash.report(e);
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public static String getAssociatedImagesList(String apiKey, Integer examId) throws RequestException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(GETASSOCIATEDIMAGESLIST + "?apiKey=" + apiKey + "&examId=" + examId)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() == INTERNAL_SERVER_ERROR_CODE || response.code() == NOT_FOUND_CODE || response.code() == FORBIDDEN_CODE) {
                throw new RequestException();
            }
            return response.body().string();
        } catch (ConnectException e) {
            //ignore
        } catch (IOException e) {
            FirebaseCrash.report(e);
            e.printStackTrace();
        }

        return null;
    }
}
