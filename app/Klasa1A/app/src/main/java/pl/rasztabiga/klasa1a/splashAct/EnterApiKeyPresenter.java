package pl.rasztabiga.klasa1a.splashAct;

import android.support.v4.app.LoaderManager;

import pl.rasztabiga.klasa1a.data.source.apiKey.ValidateApiKeyLoader;

public class EnterApiKeyPresenter implements EnterApiKeyContract.Presenter, LoaderManager.LoaderCallbacks<Boolean> {

    private final static int VALIDATE_API_KEY_QUERY = 3;

    private final ValidateApiKeyLoader mLoader;



}
