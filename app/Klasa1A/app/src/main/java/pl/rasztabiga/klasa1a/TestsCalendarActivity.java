package pl.rasztabiga.klasa1a;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TestsCalendarActivity extends AppCompatActivity {

    private static final String TAG = TestsCalendarActivity.class.getName();
    CompactCalendarView compactCalendarView;
    ListView eventList;
    TextView debug_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests_calendar);

        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        eventList = (ListView) findViewById(R.id.event_list);
        debug_tv = (TextView) findViewById(R.id.debug_tv);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.getDefault());
        String dateInString = "14-12-2016 00:00:00";
        Date date = null;
        try {
            date = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final Event ev1 = new Event(Color.GREEN, date.getTime() , "Something");
        compactCalendarView.addEvent(ev1, false);

        dateInString = "25-12-2016 00:00:00";
        try {
            date = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Event ev2 = new Event(Color.BLUE, date.getTime(), "Merry Christmas");
        compactCalendarView.addEvent(ev2, false);

        Log.d("lop;", "Events: " + compactCalendarView.getEvents(date.getTime()));

        Event ev3 = new Event(Color.BLUE, date.getTime(), "Lel");
        compactCalendarView.addEvent(ev3, false);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);

                //TODO put there recyclerview
                /*for (Event e : events) {
                    debug_tv.setText("Processing" + e.toString());
                    TableRow row = new TableRow(getApplicationContext());
                    row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    TextView tv = new TextView(getApplicationContext());
                    tv.setText(e.getData().toString());
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    row.addView(tv);
                    eventList.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    Log.d(TAG, "added row");
                }*/
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });
    }
}
