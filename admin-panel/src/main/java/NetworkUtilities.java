import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.ConnectException;

public class NetworkUtilities {

    public static String getStudents(String apiKey) throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://localhost:8007/getStudents?apiKey=" + apiKey)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 500 || response.code() == 404 || response.code() == 403) {
                throw new Exception();
            }
            return response.body().string();
        } catch (ConnectException e) {
            //ignore
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
