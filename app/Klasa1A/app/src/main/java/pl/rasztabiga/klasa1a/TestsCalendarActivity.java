package pl.rasztabiga.klasa1a;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.rasztabiga.klasa1a.models.Exam;
import pl.rasztabiga.klasa1a.utils.ExamAdapter;

public class TestsCalendarActivity extends AppCompatActivity {

    private static final String TAG = TestsCalendarActivity.class.getName();
    private final Calendar calendar = Calendar.getInstance();
    private final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
    CompactCalendarView compactCalendarView;
    TextView date_tv;
    RecyclerView mRecyclerView;
    ExamAdapter mExamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests_calendar);

        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        date_tv = (TextView) findViewById(R.id.date_tv);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_exams);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mExamAdapter = new ExamAdapter();

        mRecyclerView.setAdapter(mExamAdapter);

        Exam exam1 = new Exam("EDB", "SPR1");
        final Event ev1 = createNewEvent(2016, 11, 14, exam1);
        compactCalendarView.addEvent(ev1, false);

        Exam exam2 = new Exam("FIZYKA", "JĄDROWA");
        Event ev2 = createNewEvent(2016, 11, 25, exam2);
        compactCalendarView.addEvent(ev2, false);

        Exam exam3 = new Exam("MATEMATYKA", "SPR1");
        Event ev3 = createNewEvent(2016, 11, 25, exam3);
        compactCalendarView.addEvent(ev3, false);

        Exam exam4 = new Exam("PODSTAWY PRZEDSIĘBIORCZOŚCI", "spr1");
        Event ev4 = createNewEvent(2016, 11, 25, exam4);
        compactCalendarView.addEvent(ev4, false);

        Exam exam5 = new Exam("EDUKACJA DLA BEZPIECZEŃSTWA", "Test z działu 2 - Banki i giełda");
        Event ev5 = createNewEvent(2016, 11, 25, exam5);
        compactCalendarView.addEvent(ev5, false);

        date_tv.setText(dateFormat.format(new Date()));

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
                calendar.setTime(dateClicked);

                date_tv.setText(dateFormat.format(dateClicked));

                ArrayList<Exam> examArrayList = new ArrayList<>();
                for(Event e : events) {
                    examArrayList.add((Exam) e.getData());
                }

                mExamAdapter.setExamsData(examArrayList);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });
    }

    public Event createNewEvent(int year, int month, int day, Exam exam)
    {
        calendar.set(year, month, day);
        Date date = calendar.getTime();

        return new Event(Color.GREEN, date.getTime(), exam);

    }

}
