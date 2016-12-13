package pl.rasztabiga.klasa1a;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TestsCalendarActivity extends AppCompatActivity {

    private static final String TAG = "heheiksde";
    CompactCalendarView compactCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests_calendar);

        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.getDefault());
        String dateInString = "14-12-2016 00:00:00";
        Date date = null;
        try {
            date = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d(TAG, date.toString());
        Event ev1 = new Event(Color.GREEN, date.getTime() , "Something");
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

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });
    }
}
