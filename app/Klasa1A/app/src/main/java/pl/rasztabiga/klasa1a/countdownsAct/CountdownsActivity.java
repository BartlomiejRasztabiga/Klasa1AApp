package pl.rasztabiga.klasa1a.countdownsAct;


import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import pl.rasztabiga.klasa1a.R;
import pl.rasztabiga.klasa1a.utils.LayoutUtils;

public final class CountdownsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdowns);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LayoutUtils.getNavigationDrawer(CountdownsActivity.this, 3, toolbar);

        inflateCountDownTimer(2017, 5, 23, 0, 0, getString(R.string.holidays));
        inflateCountDownTimer(2019, 4, 6, 0, 0, getString(R.string.mature_exam));
    }

    private void inflateCountDownTimer(int year, int month, int day, int hour, int minute, final String countdownTitleString) {
        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.countdowns_layout);
        View countdown = getLayoutInflater().inflate(R.layout.countdown_fragment, rootLayout, false);
        rootLayout.addView(countdown);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        final TextView countdownTitle = (TextView) countdown.findViewById(R.id.countdownTitle);
        final TextView countdownTime = (TextView) countdown.findViewById(R.id.countdownTime);
        countdownTitle.setText(countdownTitleString + " za: ");

        //TODO This is showing one hour less than it should, fix this (it may be related with timezones)
        Long timeInMilis = (calendar.getTimeInMillis()) - System.currentTimeMillis();

        new CountDownTimer(timeInMilis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                millisUntilFinished -= TimeUnit.DAYS.toMillis(days);
                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                millisUntilFinished -= TimeUnit.HOURS.toMillis(hours);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);

                if(millisUntilFinished > 30000 || minutes == 0) {
                    minutes++;
                }

                countdownTime.setText(String.format(Locale.getDefault(), getString(R.string.date_pattern), days, hours, minutes));
            }

            @Override
            public void onFinish() {
                countdownTime.setText(countdownTitleString + "!");
                countdownTime.setTextColor(Color.RED);
            }


        }.start();

    }
}
