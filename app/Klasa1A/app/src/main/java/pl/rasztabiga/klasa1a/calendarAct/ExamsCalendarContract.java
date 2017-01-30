package pl.rasztabiga.klasa1a.calendarAct;

import java.util.List;

import pl.rasztabiga.klasa1a.BasePresenter;
import pl.rasztabiga.klasa1a.BaseView;
import pl.rasztabiga.klasa1a.data.models.Exam;

public interface ExamsCalendarContract {

    interface View extends BaseView<Presenter> {

        void getExams(List<Exam> exams);

        void showExamsOnCurrentDay(List<Exam> exams);

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadExams(boolean forceUpdate);

    }
}
