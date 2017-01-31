package pl.rasztabiga.klasa1a.data.source.examPhotos;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.List;

import pl.rasztabiga.klasa1a.RequestException;
import pl.rasztabiga.klasa1a.data.source.exams.models.Exam;
import pl.rasztabiga.klasa1a.utils.ApiKeyUtils;
import pl.rasztabiga.klasa1a.utils.NetworkUtilities;

public class ExamsPhotosLoader extends AsyncTaskLoader<List<String>> {

    private int mExamIdToFetchImages;

    private WeakReference<Context> mContext;


    public ExamsPhotosLoader(Context context) {
        super(context);
        mContext = new WeakReference<>(context);
    }

    @Override
    public List<String> loadInBackground() {
        try {
            return processImagesUrls(NetworkUtilities.getAssociatedImagesList(ApiKeyUtils.getApiKey(mContext.get()), mExamIdToFetchImages));
        } catch (RequestException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    private List<String> processImagesUrls(String data) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<String>>() {
        }.getType();
        return gson.fromJson(data, collectionType);
    }

    public void setExamIdToFetchImages(Exam exam) {
        this.mExamIdToFetchImages = exam.getId();
    }
}
