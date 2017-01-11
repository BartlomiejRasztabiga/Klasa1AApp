package pl.rasztabiga.klasa1a;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.rasztabiga.klasa1a.utils.LayoutUtils;

public class CountdownsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv1)
    TextView tv1;

    @BindView(R.id.tv1_title)
    TextView tv1Title;

    @BindView(R.id.tv2)
    TextView tv2;

    @BindView(R.id.tv2_title)
    TextView tv2Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdowns);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        LayoutUtils.getNavigationDrawer(CountdownsActivity.this, 3, toolbar);

        Calendar calendar = Calendar.getInstance();
        Calendar presentTimeCalendar = Calendar.getInstance();
        calendar.set(2017, 5, 23, 0, 0);

        //TODO zrobic to bardziej ogolne, z latwym konstruktorem i inflatowaniem

        tv1Title.setText("Wakacje za: ");
        Long timeInMilis = calendar.getTimeInMillis() - presentTimeCalendar.getTimeInMillis();
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

                tv1.setText(String.format(Locale.getDefault(), "%d d  %d h  %d min  %d sek", days, hours, minutes, seconds));
            }

            @Override
            public void onFinish() {
                tv1.setText("Wakacje!");
                tv1.setTextColor(Color.RED);
            }


        }.start();

        calendar.set(2017, 0, 28, 0, 50);
        tv2Title.setText("Ferie za: ");
        timeInMilis = calendar.getTimeInMillis() - presentTimeCalendar.getTimeInMillis();
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

                tv2.setText(String.format(Locale.getDefault(), "%d d  %d h  %d min  %d sek", days, hours, minutes, seconds));
            }

            @Override
            public void onFinish() {
                tv2.setText("Ferie!");
                tv2.setTextColor(Color.RED);
            }


        }.start();

    }
}
