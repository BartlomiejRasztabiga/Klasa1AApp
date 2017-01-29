package pl.rasztabiga.klasa1a.data.source;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import pl.rasztabiga.klasa1a.Injection;
import pl.rasztabiga.klasa1a.data.OnDuties;

import static com.google.common.base.Preconditions.checkNotNull;

public class OnDutiesLoader extends AsyncTaskLoader<OnDuties> implements OnDutiesRepository.OnDutiesRepositoryObserver {

    private OnDutiesRepository mRepository;

    public OnDutiesLoader(Context context, @NonNull OnDutiesRepository repository) {
        super(context);
        checkNotNull(repository);
        mRepository = Injection.provideOnDutiesRepository(context);
    }

    @Override
    public OnDuties loadInBackground() {
        return mRepository.getOnDuties();
    }

    @Override
    public void deliverResult(OnDuties data) {
        if (isReset()) {
            return;
        }

        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {

        // Deliver any previously loaded data immediately if available.
        if (mRepository.cachedOnDutiesAvailable()) {
            deliverResult(mRepository.getCachedOnDuties());
        }

        // Begin monitoring the underlying data source.
        mRepository.addContentObserver(this);

        if (takeContentChanged() || !mRepository.cachedOnDutiesAvailable()) {
            // When a change has  been delivered or the repository cache isn't available, we force
            // a load.
            forceLoad();
        }
    }

    @Override
    protected void onReset() {
        onStopLoading();
        mRepository.removeContentObserver(this);
    }

    @Override
    public void onTasksChanged() {
        if (isStarted()) {
            forceLoad();
        }
    }
}
