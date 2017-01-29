package pl.rasztabiga.klasa1a.data.source;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import pl.rasztabiga.klasa1a.data.OnDuties;

import static com.google.common.base.Preconditions.checkNotNull;

public class OnDutiesRepository implements OnDutiesDataSource {

    private static OnDutiesRepository instance = null;

    private final OnDutiesDataSource mOnDutiesRemoteDataSource;

    private final OnDutiesDataSource mOnDutiesLocalDataSource;
    OnDuties mCachedOnDuties;
    boolean mCacheIsDirty;
    private List<OnDutiesRepositoryObserver> mObservers = new ArrayList<>();

    private OnDutiesRepository(@NonNull OnDutiesDataSource onDutiesRemoteDataSource,
                               @NonNull OnDutiesDataSource onDutiesLocalDataSource) {
        mOnDutiesRemoteDataSource = checkNotNull(onDutiesRemoteDataSource);
        mOnDutiesLocalDataSource = checkNotNull(onDutiesLocalDataSource);
    }

    public static OnDutiesRepository getInstance(OnDutiesDataSource onDutiesRemoteDataSource,
                                                 OnDutiesDataSource onDutiesLocalDataSource) {
        if (instance == null) {
            instance = new OnDutiesRepository(onDutiesRemoteDataSource, onDutiesLocalDataSource);
        }

        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }

    public void addContentObserver(OnDutiesRepositoryObserver observer) {
        if (!mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    public void removeContentObserver(OnDutiesRepositoryObserver observer) {
        if (mObservers.contains(observer)) {
            mObservers.remove(observer);
        }
    }

    private void notifyContentObserver() {
        for (OnDutiesRepositoryObserver observer : mObservers) {
            observer.onTasksChanged();
        }
    }

    @Nullable
    @Override
    public OnDuties getOnDuties() {

        OnDuties onDuties = null;

        //TODO Uncomment later
        /*if (!mCacheIsDirty) {
            // Respond immediately with cache if available and not dirty
            if (mCachedOnDuties != null) {
                onDuties = getCachedOnDuties();
                return onDuties;
            } else {
                // Query the local storage if available.
                onDuties = mOnDutiesLocalDataSource.getOnDuties();
            }
        }*/


        // To simplify, we'll consider the local data source fresh when it has data.
        if (onDuties == null) {
            // Grab remote data if cache is dirty or local data not available.
            onDuties = mOnDutiesRemoteDataSource.getOnDuties();
            // We copy the data to the device so we don't need to query the network next time

            //TODO Uncomment
            //saveOnDutiesInLocalDataSource(onDuties);
        }

        //TODO Remove later
        onDuties = mOnDutiesRemoteDataSource.getOnDuties();

        processLoadedOnDuties(onDuties);
        return getCachedOnDuties();

    }

    public boolean cachedOnDutiesAvailable() {
        return mCachedOnDuties != null && !mCacheIsDirty;
    }

    public OnDuties getCachedOnDuties() {
        return mCachedOnDuties == null ? null : mCachedOnDuties;
    }

    public void saveOnDutiesInLocalDataSource(OnDuties onDuties) {
        if (onDuties != null) {
            mOnDutiesLocalDataSource.saveOnDuties(onDuties);
        }
    }

    private void processLoadedOnDuties(OnDuties onDuties) {
        if (onDuties == null) {
            mCachedOnDuties = null;
            mCacheIsDirty = false;
            return;
        }
        mCachedOnDuties = onDuties;
        mCacheIsDirty = false;
    }

    @Override
    public void saveOnDuties(@NonNull OnDuties onDuties) {
        checkNotNull(onDuties);
        mOnDutiesRemoteDataSource.saveOnDuties(onDuties);
        mOnDutiesLocalDataSource.saveOnDuties(onDuties);

        // Do in memory cache update to keep the app UI up to date
        mCachedOnDuties = onDuties;

        // Update the UI
        notifyContentObserver();
    }

    @Override
    public void refreshOnDuties() {
        mCacheIsDirty = true;
        notifyContentObserver();
    }

    public interface OnDutiesRepositoryObserver {

        void onTasksChanged();

    }
}
