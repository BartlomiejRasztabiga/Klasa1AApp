package pl.rasztabiga.klasa1a.countdownsAct;


import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import pl.rasztabiga.klasa1a.R;
import pl.rasztabiga.klasa1a.utils.LayoutUtils;

public class CountdownsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdowns);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LayoutUtils.getNavigationDrawer(CountdownsActivity.this, 3, toolbar);

        inflateCountDownTimer(2017, 5, 23, 0, 0, "Wakacje");
        inflateCountDownTimer(2019, 4, 6, 0, 0, "Matura");
    }

    private void inflateCountDownTimer(int year, int month, int day, int hour, int minute, final String countdownTitleString) {
        GridLayout rootLayout = (GridLayout) findViewById(R.id.countdowns_grid);
        View countdown = getLayoutInflater().inflate(R.layout.countdown_fragment, rootLayout, false);
        rootLayout.addView(countdown);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        final TextView countdownTitle = (TextView) countdown.findViewById(R.id.countdownTitle);
        final TextView countdownTime = (TextView) countdown.findViewById(R.id.countdownTime);
        countdownTitle.setText(countdownTitleString + " za: ");
        Long timeInMilis = calendar.getTimeInMillis() - new Date().getTime();

        new CountDownTimer(timeInMilis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                millisUntilFinished -= TimeUnit.DAYS.toMillis(days);
                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                millisUntilFinished -= TimeUnit.HOURS.toMillis(hours);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                countdownTime.setText(String.format(Locale.getDefault(), "%d d  %d h  %d min  %d sek", days, hours, minutes, seconds));
            }

            @Override
            public void onFinish() {
                countdownTime.setText(countdownTitle + "!");
                countdownTime.setTextColor(Color.RED);
            }


        }.start();

    }
}
