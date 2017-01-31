package pl.rasztabiga.klasa1a.data.source.luckyNumbers.local;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pl.rasztabiga.klasa1a.data.source.luckyNumbers.LuckyNumbersDataSource;
import pl.rasztabiga.klasa1a.data.source.luckyNumbers.models.LuckyNumbers;

import static com.google.common.base.Preconditions.checkNotNull;

public class LuckyNumbersLocalDataSource implements LuckyNumbersDataSource {

    private static LuckyNumbersLocalDataSource instance;

    private LuckyNumbersDbHelper mDbHelper;

    private LuckyNumbersLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new LuckyNumbersDbHelper(context);
    }

    public static LuckyNumbersLocalDataSource getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = new LuckyNumbersLocalDataSource(context);
        }

        return instance;
    }

    @Nullable
    @Override
    public LuckyNumbers getLuckyNumbers() {
        return null;
    }

    @Override
    public void saveLuckyNumbers(@NonNull LuckyNumbers luckyNumbers) {

    }

    @Override
    public void refreshLuckyNumbers() {

    }
}
