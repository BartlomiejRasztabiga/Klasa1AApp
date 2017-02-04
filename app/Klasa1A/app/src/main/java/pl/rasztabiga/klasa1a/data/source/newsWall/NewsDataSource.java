package pl.rasztabiga.klasa1a.data.source.newsWall;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import pl.rasztabiga.klasa1a.data.source.newsWall.models.News;

public interface NewsDataSource {

    @Nullable
    List<News> getNews();

    void saveNews(@NonNull List<News> news);

    void refreshNews();

    interface getNewsWallCallback {

        void onNewsLoaded(News news);

        void onDataNotAvailable();
    }
}
