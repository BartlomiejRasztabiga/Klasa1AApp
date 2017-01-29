package pl.rasztabiga.klasa1a.data.source;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pl.rasztabiga.klasa1a.data.OnDuties;

public interface OnDutiesDataSource {

    @Nullable
    OnDuties getOnDuties();

    void saveOnDuties(@NonNull OnDuties onDuties);

    void refreshOnDuties();

    interface GetOnDutiesCallback {

        void onOnDutiesLoader(OnDuties onDuties);

        void onDataNotAvailable();
    }
}
