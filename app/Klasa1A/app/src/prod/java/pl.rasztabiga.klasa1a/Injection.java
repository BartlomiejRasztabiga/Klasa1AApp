package pl.rasztabiga.klasa1a;

import android.content.Context;
import android.support.annotation.NonNull;

import pl.rasztabiga.klasa1a.data.source.OnDutiesDataSource;
import pl.rasztabiga.klasa1a.data.source.OnDutiesRepository;
import pl.rasztabiga.klasa1a.data.source.local.OnDutiesLocalDataSource;
import pl.rasztabiga.klasa1a.data.source.remote.OnDutiesRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Enables injection of production implementations for
 * {@link OnDutiesDataSource} at compile time.
 */
public class Injection {

    public static OnDutiesRepository provideOnDutiesRepository(@NonNull Context context) {
        checkNotNull(context);
        return OnDutiesRepository.getInstance(OnDutiesRemoteDataSource.getInstance(context),
                OnDutiesLocalDataSource.getInstance(context));
    }
}