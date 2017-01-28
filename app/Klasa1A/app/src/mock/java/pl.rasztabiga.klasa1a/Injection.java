package pl.rasztabiga.klasa1a;

import android.content.Context;
import android.support.annotation.NonNull;


import pl.rasztabiga.klasa1a.data.FakeOnDutiesRemoteDataSource;
import pl.rasztabiga.klasa1a.data.source.OnDutiesLoader;
import pl.rasztabiga.klasa1a.data.source.OnDutiesRepository;
import pl.rasztabiga.klasa1a.data.source.OnDutiesDataSource;
import pl.rasztabiga.klasa1a.data.source.local.OnDutiesLocalDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Enables injection of mock implementations for
 * {@link OnDutiesDataSource} at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    public static OnDutiesRepository provideOnDutiesRepository(@NonNull Context context) {
        checkNotNull(context);
        return OnDutiesRepository.getInstance(FakeOnDutiesRemoteDataSource.getInstance(),
                OnDutiesLocalDataSource.getInstance(context));
    }
}