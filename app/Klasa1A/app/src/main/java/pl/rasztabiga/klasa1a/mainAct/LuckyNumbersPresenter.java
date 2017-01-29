package pl.rasztabiga.klasa1a.mainAct;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import pl.rasztabiga.klasa1a.data.source.luckyNumbers.models.LuckyNumbers;
import pl.rasztabiga.klasa1a.data.source.luckyNumbers.LuckyNumbersLoader;
import pl.rasztabiga.klasa1a.data.source.luckyNumbers.LuckyNumbersRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class LuckyNumbersPresenter implements LuckyNumbersContract.Presenter, LoaderManager.LoaderCallbacks<LuckyNumbers> {

    //Two loaders cannot have the same ID!
    private final static int LUCKY_NUMBERS_QUERY = 2;

    private final LuckyNumbersRepository mLuckyNumbersRepository;

    private final LuckyNumbersContract.View mLuckyNumbersView;

    private final LuckyNumbersLoader mLoader;

    private final LoaderManager mLoaderManager;

    private LuckyNumbers mCurrentLuckyNumbers;

    private boolean mFirstLoad;

    public LuckyNumbersPresenter(@NonNull LuckyNumbersLoader loader, @NonNull LoaderManager loaderManager,
                                 @NonNull LuckyNumbersRepository luckyNumbersRepository, @NonNull LuckyNumbersContract.View luckyNumbersView) {
        mLoader = checkNotNull(loader, "loader cannot be null!");
        mLoaderManager = checkNotNull(loaderManager, "loader manager cannot be null");
        mLuckyNumbersRepository = checkNotNull(luckyNumbersRepository, "luckyNumbersRepository cannot be null");
        mLuckyNumbersView = checkNotNull(luckyNumbersView, "luckyNumbersView cannot be null!");

        mLuckyNumbersView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void start() {
        mLoaderManager.initLoader(LUCKY_NUMBERS_QUERY, null, this);
    }

    @Override
    public Loader<LuckyNumbers> onCreateLoader(int id, Bundle args) {
        //mLuckyNumbersView.setLoadingIndicator(true);
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<LuckyNumbers> loader, LuckyNumbers data) {
        //mLuckyNumbersView.setLoadingIndicator(false);

        mCurrentLuckyNumbers = data;
        if (mCurrentLuckyNumbers == null) {
            mLuckyNumbersView.showLoadingLuckyNumbersError();
        } else {
            showLuckyNumbers();
        }
    }

    @Override
    public void onLoaderReset(Loader<LuckyNumbers> loader) {
        //ignore
    }

    private void showLuckyNumbers() {
        LuckyNumbers luckyNumbersToDisplay = mCurrentLuckyNumbers;
        processLuckyNumbers(luckyNumbersToDisplay);
    }

    @Override
    public void loadLuckyNumbers(boolean forceUpdate) {
        if (forceUpdate || mFirstLoad) {
            mFirstLoad = false;
            mLuckyNumbersRepository.refreshLuckyNumbers();
        } else {
            showLuckyNumbers();
        }
    }

    private void processLuckyNumbers(LuckyNumbers luckyNumbers) {
        if (luckyNumbers== null) {
            processEmptyLuckyNumbers();
        } else {
            mLuckyNumbersView.showLuckyNumbers(luckyNumbers);
        }
    }

    private void processEmptyLuckyNumbers() {
        mLuckyNumbersView.showLoadingLuckyNumbersError();
    }
}
