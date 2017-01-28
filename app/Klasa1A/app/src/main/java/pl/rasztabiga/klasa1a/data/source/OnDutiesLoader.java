package pl.rasztabiga.klasa1a.data.source;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;

import pl.rasztabiga.klasa1a.data.OnDuties;
import static com.google.common.base.Preconditions.checkNotNull;

public class OnDutiesLoader extends AsyncTaskLoader<OnDuties> implements OnDutiesRepository.OnDutiesRepositoryObserver {

    private OnDutiesRepository mRepository;

    public OnDutiesLoader(Context context, @NonNull OnDutiesRepository repository) {
        super(context);
        checkNotNull(repository);
        mRepository = repository;
    }

    @Override
    public OnDuties loadInBackground() {
        return mRepository.getOnDuties();
    }
}
