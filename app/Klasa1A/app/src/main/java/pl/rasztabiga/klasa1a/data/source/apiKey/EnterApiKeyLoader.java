package pl.rasztabiga.klasa1a.data.source.apiKey;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import pl.rasztabiga.klasa1a.RequestException;
import pl.rasztabiga.klasa1a.utils.NetworkUtilities;

public class EnterApiKeyLoader extends AsyncTaskLoader<Boolean> {

    private String mApiKeyToValidate;

    public EnterApiKeyLoader(Context context) {
        super(context);
    }

    @Override
    public Boolean loadInBackground() {
        try {
            return NetworkUtilities.validateApiKey(mApiKeyToValidate);
        } catch (RequestException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /*@Override
    protected void onReset() {
        super.onReset();
        forceLoad();
    }*/

    public void setApiKeyToValidate(String apiKeyToValidate) {
        this.mApiKeyToValidate = apiKeyToValidate;
    }
}
