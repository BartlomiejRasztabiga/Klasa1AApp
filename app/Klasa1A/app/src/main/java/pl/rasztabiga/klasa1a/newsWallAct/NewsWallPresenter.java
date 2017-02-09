package pl.rasztabiga.klasa1a.newsWallAct;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.List;

import pl.rasztabiga.klasa1a.data.source.newsWall.NewsLoader;
import pl.rasztabiga.klasa1a.data.source.newsWall.NewsRepository;
import pl.rasztabiga.klasa1a.data.source.newsWall.models.News;

import static com.google.common.base.Preconditions.checkNotNull;

public class NewsWallPresenter implements NewsWallContract.Presenter, LoaderManager.LoaderCallbacks<List<News>> {

    private final static int NEWS_QUERY = 8;

    private final NewsRepository mNewsRepository;

    private final NewsWallContract.View mNewsWallView;

    private final NewsLoader mLoader;

    private final LoaderManager mLoaderManager;

    private List<News> mCurrentNews;

    private boolean mFirstLoad;

    public NewsWallPresenter(@NonNull NewsRepository repository, @NonNull NewsWallContract.View newsWallView,
                             @NonNull NewsLoader loader, @NonNull LoaderManager loaderManager) {
        mNewsRepository = checkNotNull(repository, "news repository cannot be null");
        mNewsWallView = checkNotNull(newsWallView, "news wall view cannot be null");
        mLoader = checkNotNull(loader, "loader cannot be null");
        mLoaderManager = checkNotNull(loaderManager, "loader manager cannot be null");

        mNewsWallView.setPresenter(this);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        mCurrentNews = data;
        if (mCurrentNews == null) {
            mNewsWallView.showLoadingNewsError();
        } else {
            showNews();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

    }

    @Override
    public void loadNews(boolean forceUpdate) {
        mNewsWallView.setLoadingIndicator(true);
        if (forceUpdate || mFirstLoad) {
            mFirstLoad = false;
            mNewsRepository.refreshNews();
        } else {
            showNews();
        }

    }

    private void showNews() {
        mNewsWallView.setLoadingIndicator(false);
        List<News> newsToDisplay = mCurrentNews;
        processNews(newsToDisplay);
    }

    private void processNews(List<News> news) {
        if (news == null) {
            processEmptyNews();
        } else {
            mNewsWallView.showNews(news);
        }
    }

    private void processEmptyNews() {
        mNewsWallView.showLoadingNewsError();
    }


    @Override
    public void start() {
        mLoaderManager.initLoader(NEWS_QUERY, null, this);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }
}
