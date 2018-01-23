package pl.rasztabiga.klasa1a.data.source.exams.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.List;

import pl.rasztabiga.klasa1a.RequestException;
import pl.rasztabiga.klasa1a.data.source.exams.ExamsDataSource;
import pl.rasztabiga.klasa1a.data.source.exams.models.Exam;
import pl.rasztabiga.klasa1a.utils.ApiKeyUtils;
import pl.rasztabiga.klasa1a.utils.NetworkUtilities;


public class ExamsRemoteDataSource implements ExamsDataSource {

    private static ExamsRemoteDataSource instance;
    private static WeakReference<Context> sContext;

    private ExamsRemoteDataSource() {

    }

    public static ExamsRemoteDataSource getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = new ExamsRemoteDataSource();
            sContext = new WeakReference<>(context);
        }

        return instance;
    }

    @Nullable
    @Override
    public List<Exam> getExams() {
        String response = null;
        try {
            response = NetworkUtilities.getExams(ApiKeyUtils.getApiKey(sContext.get()));
        } catch (RequestException e) {
            e.printStackTrace();
        }

        if (response != null) {
            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<Exam>>() {
            }.getType();
            List<Exam> examsList = gson.fromJson(response, collectionType);
            return examsList;
        }

        return null;
    }

    @Override
    public void refreshExams() {

    }
}
