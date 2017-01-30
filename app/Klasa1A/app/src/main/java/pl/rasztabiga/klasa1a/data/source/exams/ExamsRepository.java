package pl.rasztabiga.klasa1a.data.source.exams;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import pl.rasztabiga.klasa1a.data.source.exams.models.Exam;
import static com.google.common.base.Preconditions.checkNotNull;

public class ExamsRepository implements ExamsDataSource {

    private static ExamsRepository instance = null;

    private final ExamsDataSource mExamsRemoteDataSource;

    private final ExamsDataSource mExamsLocalDataSource;

    private List<ExamsRepositoryObserver> mObservers = new ArrayList<>();

    List<Exam> mCachedExams;

    boolean mCacheIsDirty;

    public static ExamsRepository getInstance(ExamsDataSource examsRemoteDataSource,
                                              ExamsDataSource examsLocalDataSource) {
        if (instance == null) {
            instance = new ExamsRepository(examsRemoteDataSource, examsLocalDataSource);
        }

        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }

    private ExamsRepository(@NonNull ExamsDataSource examsRemoteDataSource,
                            @NonNull ExamsDataSource tasksLocalDataSource) {
        mExamsRemoteDataSource = checkNotNull(examsRemoteDataSource);
        mExamsLocalDataSource = checkNotNull(tasksLocalDataSource);
    }

    public void addContentObserver(ExamsRepositoryObserver observer) {
        if (!mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    public void removeContentObserver(ExamsRepositoryObserver observer) {
        if (mObservers.contains(observer)) {
            mObservers.remove(observer);
        }
    }

    private void notifyContentObserver() {
        for (ExamsRepositoryObserver observer : mObservers) {
            observer.onExamsChanged();
        }
    }

    @Nullable
    @Override
    public List<Exam> getExams() {

        List<Exam> exams = null;

        //TODO Uncomment later
        /*if (!mCacheIsDirty) {
            if (mCachedExams != null) {
                exams = getCachedExams();
                return exams;
            } else {
                exams = mExamsLocalDataSource.getExams();
            }
        }*/

        if (exams == null) {
            exams = mExamsRemoteDataSource.getExams();

            //TODO Uncomment
            //saveExamsInLocalDataSource(exams);
        }

        //TODO Remove later
        //exams = mExamsRemoteDataSource.getExams();

        processLoadedExams(exams);
        return getCachedExams();
    }

    public boolean cachedExamsAvailable() {
        return mCachedExams != null && !mCacheIsDirty;
    }

    public List<Exam> getCachedExams() {
        return mCachedExams == null ? null : mCachedExams;
    }

    /*public void saveExamsInLocalDataSource(List<Exam> exams) {
        if (exams != null && !exams.isEmpty()) {
            for (Exam exam : exams) {
                mExamsLocalDataSource.saveExam(exam);
            }
        }
    }*/

    private void processLoadedExams(List<Exam> exams) {
        if (exams == null) {
            mCachedExams = null;
            mCacheIsDirty = false;
            return;
        }
        if (mCachedExams == null) {
            mCachedExams = new ArrayList<>();
        }
        mCachedExams.clear();
        for (Exam exam : exams) {
            mCachedExams.add(exam);
        }
        mCacheIsDirty = false;
    }

    @Override
    public void refreshExams() {
        mCacheIsDirty = true;
        notifyContentObserver();
    }

    public interface ExamsRepositoryObserver {

        void onExamsChanged();
    }

}
