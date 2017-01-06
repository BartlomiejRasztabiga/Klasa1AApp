package pl.rasztabiga.klasa1a;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.rasztabiga.klasa1a.models.Exam;
import pl.rasztabiga.klasa1a.models.ExamAdapter;
import pl.rasztabiga.klasa1a.utils.NetworkUtilities;

public class ExamsCalendarActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, ExamAdapter.ExamClickListener {

    private static final String TAG = ExamsCalendarActivity.class.getName();
    private static final int GET_EXAMS_LOADER = 33;
    private final Calendar calendar = Calendar.getInstance();
    private final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("LLLL yyyy", Locale.getDefault());
    private CompactCalendarView compactCalendarView;
    private TextView date_tv;
    private RecyclerView mRecyclerView;
    private ExamAdapter mExamAdapter;

    private SharedPreferences preferences;
    private String apiKey;

    private List<Exam> actualDayExamsArrayList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests_calendar);

        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        date_tv = (TextView) findViewById(R.id.date_tv);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_exams);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        apiKey = preferences.getString(getString(R.string.apiKey_pref_key), "");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mExamAdapter = new ExamAdapter(this);

        mRecyclerView.setAdapter(mExamAdapter);

        getSupportLoaderManager().initLoader(GET_EXAMS_LOADER, null, this);


        //Show date and events for actual day
        Date presentDate = new Date();
        date_tv.setText(dateFormat.format(presentDate));
        getSupportActionBar().setTitle(simpleDateFormat.format(presentDate));

        //TODO Use joda time

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                calendar.setTime(dateClicked);

                date_tv.setText(dateFormat.format(dateClicked));

                ArrayList<Exam> examArrayList = new ArrayList<>();
                for (Event e : events) {
                    examArrayList.add((Exam) e.getData());
                }

                mExamAdapter.setExamsData(examArrayList);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                getSupportActionBar().setTitle(simpleDateFormat.format(firstDayOfNewMonth));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the MainActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case GET_EXAMS_LOADER: {
                return new AsyncTaskLoader<String>(this) {
                    String examsJson;

                    @Override
                    protected void onStartLoading() {
                        if (examsJson != null) {
                            deliverResult(examsJson);
                        } else {
                            forceLoad();
                        }
                    }

                    @Override
                    public String loadInBackground() {
                        try {
                            return NetworkUtilities.getExams(apiKey);
                        } catch (RequestException e) {
                            Log.d(TAG, e.getMessage());
                            return null;
                        }
                    }

                    @Override
                    public void deliverResult(String data) {
                        examsJson = data;
                        super.deliverResult(data);
                    }
                };
            }
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        switch (loader.getId()) {
            case GET_EXAMS_LOADER: {
                if (data != null && !data.equals("")) {
                    setEvents(data);
                }

                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    private void setEvents(String JSONString) {
        try {
            JSONArray json = new JSONArray(JSONString);

            actualDayExamsArrayList = new ArrayList<>();

            for (int i = 0; i < json.length(); i++) {
                JSONObject obj = json.getJSONObject(i);
                Exam exam = new Exam(obj.getString("subject"), obj.getString("desc"), obj.getInt("year"),
                        obj.getInt("month"), obj.getInt("day"));
                actualDayExamsArrayList.add(exam);
            }

            ArrayList<Event> eventArrayList = new ArrayList<>();
            for (Exam e : actualDayExamsArrayList) {
                eventArrayList.add(e.createEvent());
            }

            compactCalendarView.addEvents(eventArrayList);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onExamClick(int clickedItemIndex) {
        Toast.makeText(this, actualDayExamsArrayList.get(clickedItemIndex).toString(), Toast.LENGTH_SHORT).show();
    }
}
