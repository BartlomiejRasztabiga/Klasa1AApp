package pl.rasztabiga.klasa1a.data.source.exams;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import pl.rasztabiga.klasa1a.data.source.exams.models.Exam;

import static com.google.common.base.Preconditions.checkNotNull;

public class ExamsLoader extends AsyncTaskLoader<List<Exam>> implements ExamsRepository.ExamsRepositoryObserver {

    private ExamsRepository mRepository;

    public ExamsLoader(Context context, @NonNull ExamsRepository repository) {
        super(context);
        mRepository = checkNotNull(repository);
    }

    @Override
    public List<Exam> loadInBackground() {
        return mRepository.getExams();
    }

    @Override
    public void deliverResult(List<Exam> data) {
        if (isReset()) {
            return;
        }

        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mRepository.cachedExamsAvailable()) {
            deliverResult(mRepository.getCachedExams());
        }

        mRepository.addContentObserver(this);

        if (takeContentChanged() || !mRepository.cachedExamsAvailable()) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
        mRepository.removeContentObserver(this);
    }

    @Override
    public void onExamsChanged() {
        if (isStarted()) {
            forceLoad();
        }
    }
}
