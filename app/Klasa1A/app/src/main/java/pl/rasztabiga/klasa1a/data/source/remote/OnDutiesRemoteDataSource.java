package pl.rasztabiga.klasa1a.data.source.remote;


import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;

import pl.rasztabiga.klasa1a.RequestException;
import pl.rasztabiga.klasa1a.data.OnDuties;
import pl.rasztabiga.klasa1a.data.source.OnDutiesDataSource;
import pl.rasztabiga.klasa1a.utils.ApiKeyUtils;
import pl.rasztabiga.klasa1a.utils.NetworkUtilities;

public class OnDutiesRemoteDataSource implements OnDutiesDataSource {

    private static OnDutiesRemoteDataSource instance;
    private static WeakReference<Context> sContext;

    public static OnDutiesRemoteDataSource getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = new OnDutiesRemoteDataSource();
            sContext = new WeakReference<>(context);
        }

        return instance;
    }

    private OnDutiesRemoteDataSource() {}

    public OnDuties getOnDuties() {
        String response = null;
        try {
            response =  NetworkUtilities.getOnDuties(ApiKeyUtils.getApiKey(sContext.get()));
        } catch (RequestException e) {
            e.printStackTrace();
        }

        if (response != null) {
            Gson gson = new Gson();
            OnDuties onDuties = gson.fromJson(response, OnDuties.class);
            return onDuties;
        }
        return null;
    }

    @Override
    public void saveOnDuties(@NonNull OnDuties onDuties) {

    }

    @Override
    public void refreshOnDuties() {

    }
}
