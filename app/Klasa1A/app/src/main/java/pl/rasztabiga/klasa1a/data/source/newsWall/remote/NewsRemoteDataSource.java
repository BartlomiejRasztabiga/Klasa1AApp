package pl.rasztabiga.klasa1a.data.source.newsWall.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.List;

import pl.rasztabiga.klasa1a.RequestException;
import pl.rasztabiga.klasa1a.data.source.newsWall.NewsDataSource;
import pl.rasztabiga.klasa1a.data.source.newsWall.models.News;
import pl.rasztabiga.klasa1a.utils.ApiKeyUtils;
import pl.rasztabiga.klasa1a.utils.NetworkUtilities;

public class NewsRemoteDataSource implements NewsDataSource {

    private static NewsRemoteDataSource instance;
    private static WeakReference<Context> sContext;


    private NewsRemoteDataSource() {

    }

    public static NewsRemoteDataSource getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = new NewsRemoteDataSource();
            sContext = new WeakReference<>(context);
        }

        return instance;
    }
    @Nullable
    @Override
    public List<News> getNews() {
        String response = null;
        try {
            response = NetworkUtilities.getNews(ApiKeyUtils.getApiKey(sContext.get()));
        } catch (RequestException e) {
            e.printStackTrace();
        }

        if (response != null){
            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<News>>(){
            }.getType();
            List<News> news = gson.fromJson(response, collectionType);
            return news;
        }
        return null;
    }




    @Override
    public void saveNews(@NonNull List<News> news) {

    }

    @Override
    public void refreshNews() {

    }
}
