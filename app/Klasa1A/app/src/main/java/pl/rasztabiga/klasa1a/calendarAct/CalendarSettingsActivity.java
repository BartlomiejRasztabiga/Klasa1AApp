package pl.rasztabiga.klasa1a.calendarAct;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import pl.rasztabiga.klasa1a.R;

public class CalendarSettingsActivity extends AppCompatActivity {

    private static final String TAG = "CalendarSettingsActivit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the VisualizerActivity
        if (id == android.R.id.home) {

            /*Intent parentActivity = new Intent(this, ExamsCalendarActivity.class);
            startActivity(parentActivity);
            finish();*/

            NavUtils.navigateUpFromSameTask(this);

        }
        return super.onOptionsItemSelected(item);
    }
}
