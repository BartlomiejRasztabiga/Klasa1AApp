package pl.rasztabiga.klasa1a.calendarAct;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import pl.rasztabiga.klasa1a.R;
import pl.rasztabiga.klasa1a.mainAct.MainActivity;
import pl.rasztabiga.klasa1a.utils.LayoutUtils;

public class ExamsCalendarActivity extends AppCompatActivity {

    private ExamsPresenter mExamsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams_calendar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LayoutUtils.getNavigationDrawer(ExamsCalendarActivity.this, 2, toolbar);


    }
}
