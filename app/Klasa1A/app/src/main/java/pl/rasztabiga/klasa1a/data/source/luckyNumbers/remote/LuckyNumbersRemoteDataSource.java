package pl.rasztabiga.klasa1a.data.source.luckyNumbers.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;

import pl.rasztabiga.klasa1a.RequestException;
import pl.rasztabiga.klasa1a.data.source.luckyNumbers.LuckyNumbersDataSource;
import pl.rasztabiga.klasa1a.data.source.luckyNumbers.models.LuckyNumbers;
import pl.rasztabiga.klasa1a.utils.ApiKeyUtils;
import pl.rasztabiga.klasa1a.utils.NetworkUtilities;

public class LuckyNumbersRemoteDataSource implements LuckyNumbersDataSource {

    private static LuckyNumbersRemoteDataSource instance;
    private static WeakReference<Context> sContext;

    private LuckyNumbersRemoteDataSource() {

    }

    public static LuckyNumbersRemoteDataSource getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = new LuckyNumbersRemoteDataSource();
            sContext = new WeakReference<>(context);
        }

        return instance;
    }

    public LuckyNumbers getLuckyNumbers() {
        String response = null;
        try {
            response = NetworkUtilities.getLuckyNumbers(ApiKeyUtils.getApiKey(sContext.get()));
        } catch (RequestException e) {
            e.printStackTrace();
        }

        if (response != null) {
            Gson gson = new Gson();
            LuckyNumbers luckyNumbers = gson.fromJson(response, LuckyNumbers.class);
            return luckyNumbers;
        }
        return null;
    }

    @Override
    public void saveLuckyNumbers(@NonNull LuckyNumbers luckyNumbers) {

    }

    @Override
    public void refreshLuckyNumbers() {

    }
}
