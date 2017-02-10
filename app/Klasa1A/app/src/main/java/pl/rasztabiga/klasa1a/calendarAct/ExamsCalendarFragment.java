package pl.rasztabiga.klasa1a.calendarAct;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.rasztabiga.klasa1a.R;
import pl.rasztabiga.klasa1a.data.source.exams.models.Exam;
import pl.rasztabiga.klasa1a.data.source.exams.models.ExamAdapter;

import static com.google.common.base.Preconditions.checkNotNull;

public class ExamsCalendarFragment extends Fragment implements ExamsCalendarContract.View, SharedPreferences.OnSharedPreferenceChangeListener {

    private final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
    private final SimpleDateFormat monthDateFormat = new SimpleDateFormat("LLLL yyyy", Locale.getDefault());
    @BindView(R.id.date_tv)
    TextView dateTextView;
    @BindView(R.id.compactcalendar_view)
    CompactCalendarView compactCalendarView;
    @BindView(R.id.recyclerview_exams)
    RecyclerView examsRecyclerView;
    private ExamsCalendarContract.Presenter mExamsPresenter;
    private ExamAdapter mExamAdapter;
    private ExamsPhotosContract.Presenter mExamsPhotosPresenter;
    ExamAdapter.ExamClickListener mItemListener = new ExamAdapter.ExamClickListener() {
        @Override
        public void onExamClick(int clickedItemIndex) {
            //prepareImagesUris(mExamAdapter.getExamList().get(clickedItemIndex));
            mExamsPhotosPresenter = new ExamsPhotosPresenter(
                    mExamAdapter.getExamList().get(clickedItemIndex),
                    getActivity().getSupportLoaderManager(),
                    getContext(),
                    getActivity()
            );

            mExamsPhotosPresenter.start();
            mExamsPhotosPresenter.getImagesUrls();
        }
    };

    public ExamsCalendarFragment() {

    }

    public static ExamsCalendarFragment newInstance() {
        return new ExamsCalendarFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        int calendarBackgroundColor = sharedPreferences.getInt("calendarBackgroundColor", Color.parseColor("#efefef"));
        compactCalendarView.setCalendarBackgroundColor(calendarBackgroundColor);

        int currentDayBackgroundColor = sharedPreferences.getInt("currentDayBackgroundColor", Color.parseColor("#01579b"));
        compactCalendarView.setCurrentDayBackgroundColor(currentDayBackgroundColor);

        int currentSelectedDayBackgroundColor = sharedPreferences.getInt("currentSelectedDayBackgroundColor", Color.parseColor("#1e88e5"));
        compactCalendarView.setCurrentSelectedDayBackgroundColor(currentSelectedDayBackgroundColor);

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "calendarBackgroundColor": {
                int color = sharedPreferences.getInt(key, Color.parseColor("#efefef"));
                compactCalendarView.setCalendarBackgroundColor(color);
                break;
            }
            case "currentDayBackgroundColor": {
                int color = sharedPreferences.getInt(key, Color.parseColor("#01579b"));
                compactCalendarView.setCurrentDayBackgroundColor(color);
                break;
            }
            case "currentSelectedDayBackgroundColor": {
                int color = sharedPreferences.getInt(key, Color.parseColor("#1e88e5"));
                compactCalendarView.setCurrentSelectedDayBackgroundColor(color);
                break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mExamsPresenter != null) {
            mExamsPresenter.start();
        }
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
        ButterKnife.bind(this, root);

        setupSharedPreferences();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        examsRecyclerView.setLayoutManager(layoutManager);
        examsRecyclerView.setHasFixedSize(true);

        mExamAdapter = new ExamAdapter(mItemListener);
        examsRecyclerView.setAdapter(mExamAdapter);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                showExamsOnCurrentDay(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(monthDateFormat.format(firstDayOfNewMonth));
            }
        });

        return root;
    }

    public void showExamsOnCurrentDay(Date dateClicked) {
        List<Event> events = compactCalendarView.getEvents(dateClicked);
        Calendar.getInstance().setTime(dateClicked);

        dateTextView.setText(dateFormat.format(dateClicked));

        ArrayList<Exam> examArrayList = new ArrayList<>();
        for (Event e : events) {
            examArrayList.add((Exam) e.getData());
        }

        mExamAdapter.setExamsData(examArrayList);
    }

    @Override
    public void showExams(List<Exam> exams) {
        if (exams != null && !exams.isEmpty()) {

            ArrayList<Event> eventArrayList = new ArrayList<>();
            for (Exam e : exams) {
                eventArrayList.add(e.createEvent());
            }

            //TODO Workaround for displaying more and more events
            compactCalendarView.removeAllEvents();
            compactCalendarView.addEvents(eventArrayList);

            Date presentDate = new Date();
            dateTextView.setText(dateFormat.format(presentDate));
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(monthDateFormat.format(presentDate));

            List<Event> events = compactCalendarView.getEvents(presentDate);

            ArrayList<Exam> examArrayList = new ArrayList<>();
            for (Event e : events) {
                examArrayList.add((Exam) e.getData());
            }

            mExamAdapter.setExamsData(examArrayList);
        }
    }
}
