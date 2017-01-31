package pl.rasztabiga.klasa1a.data.source.luckyNumbers;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import pl.rasztabiga.klasa1a.Injection;
import pl.rasztabiga.klasa1a.data.source.luckyNumbers.models.LuckyNumbers;

import static com.google.common.base.Preconditions.checkNotNull;

public class LuckyNumbersLoader extends AsyncTaskLoader<LuckyNumbers> implements LuckyNumbersRepository.LuckyNumbersRepositoryObserver {

    private LuckyNumbersRepository mRepository;

    public LuckyNumbersLoader(Context context, @NonNull LuckyNumbersRepository repository) {
        super(context);
        checkNotNull(repository);
        mRepository = Injection.provideLuckyNumbersRepository(context);
    }

    @Override
    public LuckyNumbers loadInBackground() {
        return mRepository.getLuckyNumbers();
    }

    @Override
    public void deliverResult(LuckyNumbers data) {
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
        if (mRepository.cachedLuckyNumbersAvailable()) {
            deliverResult(mRepository.getCachedLuckyNumbers());
        }

        // Begin monitoring the underlying data source.
        mRepository.addContentObserver(this);

        if (takeContentChanged() || !mRepository.cachedLuckyNumbersAvailable()) {
            // When a change has  been delivered or the repository cache isn't available, we force
            // a load.
            forceLoad();
        }
    }

    @Override
    protected void onReset() {
        onStartLoading();
        mRepository.removeContentObserver(this);
    }

    @Override
    public void onLuckyNumbersChanged() {
        if (isStarted()) {
            forceLoad();
        }
    }
}
