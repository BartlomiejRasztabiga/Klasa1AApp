package pl.rasztabiga.klasa1a;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import pl.rasztabiga.klasa1a.data.source.LuckyNumbersRepository;
import pl.rasztabiga.klasa1a.data.source.OnDutiesDataSource;
import pl.rasztabiga.klasa1a.data.source.OnDutiesRepository;
import pl.rasztabiga.klasa1a.data.source.local.LuckyNumbersLocalDataSource;
import pl.rasztabiga.klasa1a.data.source.local.OnDutiesLocalDataSource;
import pl.rasztabiga.klasa1a.data.source.remote.LuckyNumbersRemoteDataSource;
import pl.rasztabiga.klasa1a.data.source.remote.OnDutiesRemoteDataSource;

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

    public static LuckyNumbersRepository proviceLuckyNumbersRepository(@NonNull Context context) {
        checkNotNull(context);
        return LuckyNumbersRepository.getInstance(LuckyNumbersRemoteDataSource.getInstance(context),
                LuckyNumbersLocalDataSource.getInstance(context));
    }

    public static String provideServerAddress() {
        if ((BuildConfig.FLAVOR + BuildConfig.BUILD_TYPE).equals("prodrelease")) {
            Log.d("LOL", "http://89.36.219.95:8007");
            return "http://89.36.219.95:8007";
        }
        Log.d("LOL", "http://94.177.229.18:8007");
        return "http://94.177.229.18:8007";
    }
}
