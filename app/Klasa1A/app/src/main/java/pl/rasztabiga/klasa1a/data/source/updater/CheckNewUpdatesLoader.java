package pl.rasztabiga.klasa1a.data.source.updater;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.lang.ref.WeakReference;

import pl.rasztabiga.klasa1a.RequestException;
import pl.rasztabiga.klasa1a.utils.ApiKeyUtils;
import pl.rasztabiga.klasa1a.utils.NetworkUtilities;

public class CheckNewUpdatesLoader extends AsyncTaskLoader<Integer> {

    private WeakReference<Context> mContext;

    public CheckNewUpdatesLoader(Context context) {
        super(context);
        mContext = new WeakReference<>(context);
    }

    @Override
    public Integer loadInBackground() {
        try {
            return NetworkUtilities.getActualVersion(ApiKeyUtils.getApiKey(mContext.get()));
        } catch (RequestException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        forceLoad();
    }
}
