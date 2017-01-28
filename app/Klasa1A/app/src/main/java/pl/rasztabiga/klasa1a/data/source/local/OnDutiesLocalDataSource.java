package pl.rasztabiga.klasa1a.data.source.local;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pl.rasztabiga.klasa1a.data.OnDuties;
import pl.rasztabiga.klasa1a.data.source.OnDutiesDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class OnDutiesLocalDataSource implements OnDutiesDataSource {

    private static OnDutiesLocalDataSource instance;

    private OnDutiesDbHelper mDbHelper;

    public static OnDutiesLocalDataSource getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = new OnDutiesLocalDataSource(context);
        }
        return instance;
    }

    private OnDutiesLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new OnDutiesDbHelper(context);
    }

    @Nullable
    @Override
    public OnDuties getOnDuties() {
        return null;
    }

    @Override
    public void saveOnDuties(@NonNull OnDuties onDuties) {

    }

    @Override
    public void refreshOnDuties() {

    }
}
