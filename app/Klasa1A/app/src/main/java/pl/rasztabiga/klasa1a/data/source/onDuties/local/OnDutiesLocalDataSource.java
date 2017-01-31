package pl.rasztabiga.klasa1a.data.source.onDuties.local;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pl.rasztabiga.klasa1a.data.source.onDuties.OnDutiesDataSource;
import pl.rasztabiga.klasa1a.data.source.onDuties.models.OnDuties;

import static com.google.common.base.Preconditions.checkNotNull;

public class OnDutiesLocalDataSource implements OnDutiesDataSource {

    private static OnDutiesLocalDataSource instance;

    private OnDutiesDbHelper mDbHelper;

    private OnDutiesLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new OnDutiesDbHelper(context);
    }

    public static OnDutiesLocalDataSource getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = new OnDutiesLocalDataSource(context);
        }
        return instance;
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
