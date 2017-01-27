package pl.rasztabiga.klasa1a.data.source;


import android.support.annotation.Nullable;

import butterknife.BindView;
import pl.rasztabiga.klasa1a.data.OnDuties;

public interface OnDutiesDataSource {

    interface GetOnDutiesCallback {

        void onOnDutiesLoader(OnDuties onDuties);

        void onDataNotAvailable();
    }

    @Nullable
    OnDuties getOnDuties();

    void refreshOnDuties();
}
