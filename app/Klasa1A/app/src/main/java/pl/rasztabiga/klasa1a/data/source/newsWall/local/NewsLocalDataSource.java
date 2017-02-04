package pl.rasztabiga.klasa1a.data.source.newsWall.local;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import pl.rasztabiga.klasa1a.data.source.newsWall.NewsDataSource;
import pl.rasztabiga.klasa1a.data.source.newsWall.models.News;

import static com.google.common.base.Preconditions.checkNotNull;

public class NewsLocalDataSource implements NewsDataSource {

    private static NewsLocalDataSource instance;

    private NewsDbHelper mDbHelper;

    private NewsLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new NewsDbHelper(context);
    }

    public static NewsLocalDataSource getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = new NewsLocalDataSource(context);
        }
        return instance;
    }

    @Nullable
    @Override
    public List<News> getNews() {
        return null;
    }

    @Override
    public void saveNews(@NonNull List<News> news) {

    }

    @Override
    public void refreshNews() {

    }
}
