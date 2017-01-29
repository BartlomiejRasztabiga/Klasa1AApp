package pl.rasztabiga.klasa1a.data.source.luckyNumbers;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pl.rasztabiga.klasa1a.data.source.luckyNumbers.models.LuckyNumbers;

public interface LuckyNumbersDataSource {

    @Nullable
    LuckyNumbers getLuckyNumbers();

    void saveLuckyNumbers(@NonNull LuckyNumbers luckyNumbers);

    void refreshLuckyNumbers();

    interface GetLuckyNumbersCallback {

        void onLuckyNumbersLoaded(LuckyNumbers luckyNumbers);

        void onDataNotAvailable();
    }
}
