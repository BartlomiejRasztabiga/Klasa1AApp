package pl.rasztabiga.klasa1a.splashAct;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import pl.rasztabiga.klasa1a.data.source.apiKey.EnterApiKeyLoader;
import pl.rasztabiga.klasa1a.utils.PreferencesUtils;

import static com.google.common.base.Preconditions.checkNotNull;

public class EnterApiKeyPresenter implements EnterApiKeyContract.Presenter, LoaderManager.LoaderCallbacks<Boolean> {

    private final static int VALIDATE_API_KEY_QUERY = 3;

    private final EnterApiKeyContract.View mEnterApiKeyView;

    private final EnterApiKeyLoader mLoader;

    private final LoaderManager mLoaderManager;

    private Context mContext;

    private String apiKeyToValidate;

    public EnterApiKeyPresenter(@NonNull LoaderManager loaderManager, @NonNull EnterApiKeyContract.View enterApiKeyView, @NonNull Context context) {
        mLoader = new EnterApiKeyLoader(context);
        mLoaderManager = checkNotNull(loaderManager);
        mEnterApiKeyView = checkNotNull(enterApiKeyView);
        mContext = context;

        mEnterApiKeyView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void checkIsApiKeyValid(String apiKeyToValidate) {
        this.apiKeyToValidate = apiKeyToValidate;
        mLoader.setApiKeyToValidate(apiKeyToValidate);
        mLoaderManager.initLoader(VALIDATE_API_KEY_QUERY, null, this);
    }


    @Override
    public Loader<Boolean> onCreateLoader(int id, Bundle args) {
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
        if (data == null) {
            mEnterApiKeyView.showApiKeyError();
        } else {
            processApiKeyValidation(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {

    }

    private void processApiKeyValidation(boolean data) {
        if (data) {
            PreferencesUtils.saveApiKey(mContext, apiKeyToValidate);
            mEnterApiKeyView.acceptApiKey();
        } else {
            mEnterApiKeyView.showApiKeyError();
        }
    }

    @Override
    public void start() {

    }
}
