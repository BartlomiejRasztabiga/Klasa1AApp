package pl.rasztabiga.klasa1a;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import pl.rasztabiga.klasa1a.data.source.exams.ExamsRepository;
import pl.rasztabiga.klasa1a.data.source.exams.local.ExamsLocalDataSource;
import pl.rasztabiga.klasa1a.data.source.exams.remote.ExamsRemoteDataSource;
import pl.rasztabiga.klasa1a.data.source.luckyNumbers.LuckyNumbersRepository;
import pl.rasztabiga.klasa1a.data.source.onDuties.OnDutiesDataSource;
import pl.rasztabiga.klasa1a.data.source.onDuties.OnDutiesRepository;
import pl.rasztabiga.klasa1a.data.source.luckyNumbers.local.LuckyNumbersLocalDataSource;
import pl.rasztabiga.klasa1a.data.source.onDuties.local.OnDutiesLocalDataSource;
import pl.rasztabiga.klasa1a.data.source.luckyNumbers.remote.LuckyNumbersRemoteDataSource;
import pl.rasztabiga.klasa1a.data.source.onDuties.remote.OnDutiesRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Enables injection of production implementations for
 * {@link OnDutiesDataSource} at compile time.
 */
public class Injection {

    private Injection() {}

    public static OnDutiesRepository provideOnDutiesRepository(@NonNull Context context) {
        checkNotNull(context);
        return OnDutiesRepository.getInstance(OnDutiesRemoteDataSource.getInstance(context),
                OnDutiesLocalDataSource.getInstance(context));
    }

    public static LuckyNumbersRepository provideLuckyNumbersRepository(@NonNull Context context) {
        checkNotNull(context);
        return LuckyNumbersRepository.getInstance(LuckyNumbersRemoteDataSource.getInstance(context),
                LuckyNumbersLocalDataSource.getInstance(context));
    }

    public static ExamsRepository proviceExamsRepository(@NonNull Context context) {
        checkNotNull(context);
        return ExamsRepository.getInstance(ExamsRemoteDataSource.getInstance(context),
                ExamsLocalDataSource.getInstance(context));
    }

    public static String provideServerAddress() {
        if ((BuildConfig.FLAVOR + BuildConfig.BUILD_TYPE).equals("prodrelease")) {
            return "http://89.36.219.95:8007";
        }
        return "http://94.177.229.18:8007";
    }
}
