package pl.rasztabiga.klasa1a;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.rasztabiga.klasa1a.models.Exam;
import pl.rasztabiga.klasa1a.utils.ExamAdapter;
import pl.rasztabiga.klasa1a.utils.NetworkUtilities;

public class TestsCalendarActivity extends AppCompatActivity {

    private static final String TAG = TestsCalendarActivity.class.getName();
    private final Calendar calendar = Calendar.getInstance();
    private final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
    private CompactCalendarView compactCalendarView;
    private TextView date_tv;
    private RecyclerView mRecyclerView;
    private ExamAdapter mExamAdapter;

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

        new GetEventsTask().execute();

        //Show date and events for actual day
        date_tv.setText(dateFormat.format(new Date()));

        //TODO USE JODA TIME

/*        //Workaround for showing events for actual day
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        Date todayWithZeroTime = null;
        try {
            todayWithZeroTime = formatter.parse(formatter.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d(TAG, today.toString());

        List<Event> events = compactCalendarView.getEvents(todayWithZeroTime);

        ArrayList<Exam> examArrayList = new ArrayList<>();
        for(Event e : events) {
            examArrayList.add((Exam) e.getData());
        }

        Log.d(TAG, examArrayList.toString());
        mExamAdapter.setExamsData(examArrayList);*/

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

    private void setEvents(String JSONString) {
        try {
            JSONArray json = new JSONArray(JSONString);

            List<Exam> arrayList = new ArrayList<>();

            for(int i=0; i < json.length(); i++) {
                JSONObject obj = json.getJSONObject(i);
                Exam exam = new Exam(obj.getString("subject"), obj.getString("desc"), obj.getInt("year"),
                        obj.getInt("month"), obj.getInt("day"));
                arrayList.add(exam);
            }

            ArrayList<Event> eventArrayList = new ArrayList<>();
            for(Exam e : arrayList) {
                eventArrayList.add(e.createEvent());
            }

            compactCalendarView.addEvents(eventArrayList);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private class GetEventsTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return NetworkUtilities.getExams();
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {
                setEvents(s);
            }
        }
    }

}
