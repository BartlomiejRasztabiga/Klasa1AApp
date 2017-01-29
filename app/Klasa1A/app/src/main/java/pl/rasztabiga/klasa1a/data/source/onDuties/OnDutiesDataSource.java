package pl.rasztabiga.klasa1a.data.source.onDuties;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pl.rasztabiga.klasa1a.data.source.onDuties.models.OnDuties;

public interface OnDutiesDataSource {

    @Nullable
    OnDuties getOnDuties();

    void saveOnDuties(@NonNull OnDuties onDuties);

    void refreshOnDuties();

    interface GetOnDutiesCallback {

        void onOnDutiesLoaded(OnDuties onDuties);

        void onDataNotAvailable();
    }
}
