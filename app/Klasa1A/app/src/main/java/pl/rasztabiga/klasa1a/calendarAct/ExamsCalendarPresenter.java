package pl.rasztabiga.klasa1a.calendarAct;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.ArrayList;
import java.util.List;

import pl.rasztabiga.klasa1a.data.source.exams.ExamsLoader;
import pl.rasztabiga.klasa1a.data.source.exams.ExamsRepository;
import pl.rasztabiga.klasa1a.data.source.exams.models.Exam;

import static com.google.common.base.Preconditions.checkNotNull;

public class ExamsCalendarPresenter implements ExamsCalendarContract.Presenter, LoaderManager.LoaderCallbacks<List<Exam>> {

    private final static int EXAMS_QUERY = 4;

    private final ExamsRepository mExamsRepository;

    private final ExamsCalendarContract.View mExamsCalendarView;

    private final ExamsLoader mLoader;

    private final LoaderManager mLoaderManager;

    private List<Exam> mCurrentExams;

    private boolean mFirstLoad;

    public ExamsCalendarPresenter(@NonNull ExamsLoader loader, @NonNull LoaderManager loaderManager,
                                  @NonNull ExamsRepository examsRepository, @NonNull ExamsCalendarContract.View examsCalendarView) {
        mLoader = checkNotNull(loader);
        mLoaderManager = checkNotNull(loaderManager);
        mExamsRepository = checkNotNull(examsRepository);
        mExamsCalendarView = checkNotNull(examsCalendarView);

        examsCalendarView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void start() {
        mLoaderManager.initLoader(EXAMS_QUERY, null, this);
    }

    @Override
    public Loader<List<Exam>> onCreateLoader(int id, Bundle args) {
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Exam>> loader, List<Exam> data) {
        mCurrentExams = data;
        if (mCurrentExams == null) {
            //showError
        } else {
            showExams();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Exam>> loader) {

    }

    @Override
    public void loadExams(boolean forceUpdate) {
        if (forceUpdate || mFirstLoad) {
            mFirstLoad = false;
            mExamsRepository.refreshExams();
            //TODO Is refreshing done correctly?
            showExams();
        } else {
            showExams();
        }
    }

    private void showExams() {
        List<Exam> examsToDisplay = new ArrayList<>();
        if (mCurrentExams != null) {
            examsToDisplay = mCurrentExams;
        }

        processExams(examsToDisplay);
    }

    private void processExams(List<Exam> exams) {
        if (exams.isEmpty()) {
            //emptyExams
        } else {
            mExamsCalendarView.showExams(exams);
        }
    }
}
