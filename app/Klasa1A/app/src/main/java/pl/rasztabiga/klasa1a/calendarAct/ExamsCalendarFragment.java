package pl.rasztabiga.klasa1a.calendarAct;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import pl.rasztabiga.klasa1a.R;
import pl.rasztabiga.klasa1a.data.models.Exam;
import pl.rasztabiga.klasa1a.data.models.ExamAdapter;
import static com.google.common.base.Preconditions.checkNotNull;

public class ExamsCalendarFragment extends Fragment implements ExamsCalendarContract.View {

    @BindView(R.id.date_tv)
    TextView dateTextView;
    @BindView(R.id.compactcalendar_view)
    CompactCalendarView compactCalendarView;
    @BindView(R.id.recyclerview_exams)
    RecyclerView examsRecyclerView;

    private ExamsCalendarContract.Presenter mExamsPresenter;

    private ExamAdapter mExamAdapter;

    public ExamsCalendarFragment() {

    }

    public static ExamsCalendarFragment newInstance() {
        return new ExamsCalendarFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExamAdapter = new ExamAdapter(mItemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mExamsPresenter.start();
    }

    @Override
    public void setPresenter(ExamsCalendarContract.Presenter presenter) {
        mExamsPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mExamsPresenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.exams_calendar_fragment, container, false);

        examsRecyclerView.setAdapter(mExamAdapter);
        return root;
    }

    ExamAdapter.ExamClickListener mItemListener = new ExamAdapter.ExamClickListener() {
        @Override
        public void onExamClick(int clickedItemIndex) {
            
        }
    };

    @Override
    public void showExamsOnCurrentDay(List<Exam> exams) {

    }

    @Override
    public void getExams(List<Exam> exams) {
        if (exams != null && !exams.isEmpty()) {

            ArrayList<Event> eventArrayList = new ArrayList<>();
            for (Exam e : exams) {
                eventArrayList.add(e.createEvent());
            }

            compactCalendarView.addEvents(eventArrayList);
        }
    }
}
