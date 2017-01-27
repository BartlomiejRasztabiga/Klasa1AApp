package pl.rasztabiga.klasa1a.mainAct;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import static com.google.common.base.Preconditions.checkNotNull;

import pl.rasztabiga.klasa1a.data.OnDuties;
import pl.rasztabiga.klasa1a.data.source.OnDutiesLoader;
import pl.rasztabiga.klasa1a.data.source.OnDutiesRepository;

public class OnDutiesPresenter implements OnDutiesContract.Presenter, LoaderManager.LoaderCallbacks<OnDuties> {

    private final static int ON_DUTIES_QUERY = 1;

    private final OnDutiesRepository mOnDutiesRepository;

    private final OnDutiesContract.View mOnDutiesView;

    private final OnDutiesLoader mLoader;

    private final LoaderManager mLoaderManager;

    private OnDuties mCurrentOnDuties;

    private boolean mFirstLoad;

    public OnDutiesPresenter(@NonNull OnDutiesLoader loader, @NonNull LoaderManager loaderManager,
                             @NonNull OnDutiesRepository onDutiesRepository, @NonNull OnDutiesContract.View onDutiesView) {
        mLoader = checkNotNull(loader, "loader cannot be null!");
        mLoaderManager = checkNotNull(loaderManager, "loader manager cannot be null");
        mOnDutiesRepository = checkNotNull(onDutiesRepository, "tonDutiesRepository cannot be null");
        mOnDutiesView = checkNotNull(onDutiesView, "onDutiessView cannot be null!");

        mOnDutiesView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void start() {
        mLoaderManager.initLoader(ON_DUTIES_QUERY, null ,this);
    }

    @Override
    public Loader<OnDuties> onCreateLoader(int id, Bundle args) {
        mOnDutiesView.setLoadingIndicator(true);
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<OnDuties> loader, OnDuties data) {
        mOnDutiesView.setLoadingIndicator(false);

        mCurrentOnDuties = data;
        if (mCurrentOnDuties == null) {
            mOnDutiesView.showLoadingOnDutiesError();
        } else {
            showOnDuties();
        }
    }

    private void showOnDuties() {
        OnDuties onDutiesToDisplay = mCurrentOnDuties;
        processOnDuties(onDutiesToDisplay);
    }

    @Override
    public void loadOnDuties(boolean forceUpdate) {
        if (forceUpdate || mFirstLoad) {
            mFirstLoad = false;
            mOnDutiesRepository.refreshOnDuties();
        } else {
            showOnDuties();
        }
    }

    private void processOnDuties(OnDuties onDuties) {
        if (onDuties == null) {
            processEmptyOnDuties();
        } else {
            mOnDutiesView.showOnDuties(onDuties);
        }
    }

    private void processEmptyOnDuties() {
        mOnDutiesView.showLoadingOnDutiesError();
    }
}
