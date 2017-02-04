package pl.rasztabiga.klasa1a.data.source.newsWall;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import pl.rasztabiga.klasa1a.data.source.newsWall.models.News;

import static com.google.common.base.Preconditions.checkNotNull;

public class NewsRepository implements NewsDataSource {

    private static NewsRepository instance = null;

    private final NewsDataSource mNewsRemoteDataSource;

    private final NewsDataSource mNewsLocalDataSource;

    List<News> mCachedNews;

    boolean mCacheIsDirty;

    private List<NewsRepositoryObserver> mObservers = new ArrayList<>();


    private NewsRepository(@NonNull NewsDataSource newsRemoteDataSource,
                           @NonNull NewsDataSource newsLocalDataSource) {
        mNewsLocalDataSource = checkNotNull(newsLocalDataSource);
        mNewsRemoteDataSource = checkNotNull(newsRemoteDataSource);
    }

    public static NewsRepository getInstance(NewsDataSource newsWallRemoteDataSource,
                                             NewsDataSource newsWallLocalDataSource) {
        if (instance == null) {
            instance = new NewsRepository(newsWallRemoteDataSource, newsWallLocalDataSource);
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }

    public void addContentObserver(NewsRepositoryObserver observer) {
        if (!mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    public void removeContentObserver(NewsRepositoryObserver observer) {
        if (mObservers.contains(observer)) {
            mObservers.remove(observer);
        }
    }

    private void notifyContentObserver() {
        for (NewsRepositoryObserver observer : mObservers) {
            observer.onNewsChanged();
        }
    }

    @Nullable
    @Override
    public List<News> getNews() {
        List<News> news = null;

        //TODO Uncomment later
        /*if (!mCacheIsDirty) {
            // Respond immediately with cache if available and not dirty
            if (mCachedNews != null) {
                news = getCachedNews();
                return onDuties;
            } else {
                // Query the local storage if available.
                news = mNewsWallLocalDataSource.getNews();
            }
        }*/


        // To simplify, we'll consider the local data source fresh when it has data.
        if (news == null) {
            // Grab remote data if cache is dirty or local data not available.
            news = mNewsRemoteDataSource.getNews();
            // We copy the data to the device so we don't need to query the network next time

            //TODO Uncomment
            //saveNewsWallInLocalDataSource(news);
        }

        //TODO Remove later
        //news = mNewsRemoteDataSource.getNews();

        processLoadedNews(news);
        return getCachedNews();

    }

    public boolean cachedNewsAvailable() {
        return mCachedNews != null && !mCacheIsDirty;
    }

    public List<News> getCachedNews() {
        return mCachedNews == null ? null : mCachedNews;
    }

    @Override
    public void refreshNews() {
        mCacheIsDirty = true;
        notifyContentObserver();
    }

    public void saveNewsInLocalDataSource(List<News> news) {
        if (news != null) {
            mNewsLocalDataSource.saveNews(news);
        }
    }

    private void processLoadedNews(List<News> news) {
        if (news == null) {
            mCachedNews = null;
            mCacheIsDirty = false;
            return;
        }
        mCachedNews = news;
        mCacheIsDirty = false;
    }

    @Override
    public void saveNews(@NonNull List<News> news) {
        checkNotNull(news);
        mNewsRemoteDataSource.saveNews(news);
        mNewsLocalDataSource.saveNews(news);

        // Do in memory cache update to keep the app UI up to date
        mCachedNews = news;

        // Update the UI
        notifyContentObserver();
    }

    public interface NewsRepositoryObserver {

        void onNewsChanged();
    }
}
