package pl.rasztabiga.klasa1a.data.source;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import pl.rasztabiga.klasa1a.data.LuckyNumbers;

import static com.google.common.base.Preconditions.checkNotNull;

public class LuckyNumbersRepository implements LuckyNumbersDataSource {

    private static LuckyNumbersRepository instance = null;

    private final LuckyNumbersDataSource mLuckyNumbersRemoteDataSource;

    private final LuckyNumbersDataSource mLuckyNumbersLocalDataSource;

    LuckyNumbers mCachedLuckyNumbers;

    boolean mCacheIsDirty;

    private List<LuckyNumbersRepositoryObserver> mObservers = new ArrayList<>();

    private LuckyNumbersRepository(@NonNull LuckyNumbersDataSource luckyNumbersRemoteDataSource,
                                   @NonNull LuckyNumbersDataSource luckyNumbersLocalDataSource) {
        mLuckyNumbersRemoteDataSource = checkNotNull(luckyNumbersRemoteDataSource);
        mLuckyNumbersLocalDataSource = checkNotNull(luckyNumbersLocalDataSource);
    }

    public static LuckyNumbersRepository getInstance(LuckyNumbersDataSource luckyNumbersRemoteDataSource,
                                                     LuckyNumbersDataSource luckyNumbersLocalDataSource) {

        if (instance == null) {
            instance = new LuckyNumbersRepository(luckyNumbersRemoteDataSource, luckyNumbersLocalDataSource);
        }

        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }

    public void addContentObserver(LuckyNumbersRepositoryObserver observer) {
        if (!mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    public void removeContentObserver(LuckyNumbersRepositoryObserver observer) {
        if (mObservers.contains(observer)) {
            mObservers.remove(observer);
        }
    }

    private void notifyContentObserver() {
        for (LuckyNumbersRepositoryObserver observer : mObservers) {
            observer.onLuckyNumbersChanged();
        }
    }

    @Nullable
    @Override
    public LuckyNumbers getLuckyNumbers() {

        LuckyNumbers luckyNumbers = null;

        //TODO Uncomment later
        /*if (!mCacheIsDirty) {
            // Respond immediately with cache if available and not dirty
            if (mCachedLuckyNumbers != null) {
                luckyNumbers = getCachedLuckyNumbers();
                return luckyNumbers;
            } else {
                // Query the local storage if available.
                luckyNumbers = mLuckyNumbersLocalDataSource.getLuckyNumbers();
            }
        }*/


        // To simplify, we'll consider the local data source fresh when it has data.
        if (luckyNumbers == null) {
            // Grab remote data if cache is dirty or local data not available.
            luckyNumbers = mLuckyNumbersRemoteDataSource.getLuckyNumbers();
            // We copy the data to the device so we don't need to query the network next time

            //TODO Uncomment
            //saveLuckyNumbersInLocalDataSource(luckyNumbers);
        }

        //TODO Remove later
        luckyNumbers = mLuckyNumbersRemoteDataSource.getLuckyNumbers();

        processLoadedLuckyNumbers(luckyNumbers);
        return getCachedLuckyNumbers();
    }

    public boolean cachedLuckyNumbersAvailable() {
        return mCachedLuckyNumbers != null && !mCacheIsDirty;
    }

    public LuckyNumbers getCachedLuckyNumbers() {
        return mCachedLuckyNumbers == null ? null : mCachedLuckyNumbers;
    }

    public void saveLuckyNumbersInLocalDataSource(LuckyNumbers luckyNumbers) {
        if (luckyNumbers != null) {
            mLuckyNumbersLocalDataSource.saveLuckyNumbers(luckyNumbers);
        }
    }

    private void processLoadedLuckyNumbers(LuckyNumbers luckyNumbers) {
        if (luckyNumbers == null) {
            mCachedLuckyNumbers = null;
            mCacheIsDirty = false;
            return;
        }
        mCachedLuckyNumbers = luckyNumbers;
        mCacheIsDirty = false;
    }

    @Override
    public void saveLuckyNumbers(@NonNull LuckyNumbers luckyNumbers) {
        checkNotNull(luckyNumbers);
        mLuckyNumbersRemoteDataSource.saveLuckyNumbers(luckyNumbers);
        mLuckyNumbersLocalDataSource.saveLuckyNumbers(luckyNumbers);

        // Do in memory cache update to keep the app UI up to date
        mCachedLuckyNumbers = luckyNumbers;

        // Update the UI
        notifyContentObserver();
    }

    @Override
    public void refreshLuckyNumbers() {
        mCacheIsDirty = true;
        notifyContentObserver();
    }

    public interface LuckyNumbersRepositoryObserver {

        void onLuckyNumbersChanged();
    }
}
