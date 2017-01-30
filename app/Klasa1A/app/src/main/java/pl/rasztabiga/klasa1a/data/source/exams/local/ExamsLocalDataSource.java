package pl.rasztabiga.klasa1a.data.source.exams.local;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import pl.rasztabiga.klasa1a.data.source.exams.ExamsDataSource;
import pl.rasztabiga.klasa1a.data.source.exams.models.Exam;
import pl.rasztabiga.klasa1a.data.source.luckyNumbers.LuckyNumbersDataSource;
import pl.rasztabiga.klasa1a.data.source.luckyNumbers.local.LuckyNumbersDbHelper;
import pl.rasztabiga.klasa1a.data.source.luckyNumbers.models.LuckyNumbers;

import static com.google.common.base.Preconditions.checkNotNull;

public class ExamsLocalDataSource implements ExamsDataSource {

    private static ExamsLocalDataSource instance;

    private ExamsDbHelper mDbHelper;

    private ExamsLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new ExamsDbHelper();
    }

    public static ExamsLocalDataSource getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = new ExamsLocalDataSource(context);
        }

        return instance;
    }

    @Nullable
    @Override
    public List<Exam> getExams() {
        return null;
    }

    @Override
    public void refreshExams() {

    }
}
