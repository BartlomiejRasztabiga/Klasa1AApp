package pl.rasztabiga.klasa1a.data.source.newsWall;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import pl.rasztabiga.klasa1a.Injection;
import pl.rasztabiga.klasa1a.data.source.newsWall.models.News;

import static com.google.common.base.Preconditions.checkNotNull;

public class NewsLoader extends AsyncTaskLoader<List<News>> implements NewsRepository.NewsRepositoryObserver {

    private NewsRepository mRepository;

    public NewsLoader(Context context, @NonNull NewsRepository newsRepository) {
        super(context);
        checkNotNull(context);
        mRepository = Injection.provideNewsRepository(context);
    }

    @Override
    public List<News> loadInBackground() {
        return mRepository.getNews();
    }

    @Override
    public void deliverResult(List<News> data) {
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
        if (mRepository.cachedNewsAvailable()) {
            deliverResult(mRepository.getCachedNews());
        }

        // Begin monitoring the underlying data source.
        mRepository.addContentObserver(this);

        if (takeContentChanged() || !mRepository.cachedNewsAvailable()) {
            // When a change has  been delivered or the repository cache isn't available, we force
            // a load.
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
        mRepository.removeContentObserver(this);
    }

    @Override
    public void onNewsChanged() {
        if (isStarted()) {
            forceLoad();
        }
    }
}


