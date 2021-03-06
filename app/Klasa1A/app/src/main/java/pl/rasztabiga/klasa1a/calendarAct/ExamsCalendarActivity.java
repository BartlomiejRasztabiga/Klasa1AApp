package pl.rasztabiga.klasa1a.calendarAct;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import pl.rasztabiga.klasa1a.Injection;
import pl.rasztabiga.klasa1a.R;
import pl.rasztabiga.klasa1a.data.source.exams.ExamsLoader;
import pl.rasztabiga.klasa1a.data.source.exams.ExamsRepository;
import pl.rasztabiga.klasa1a.utils.LayoutUtils;

public class ExamsCalendarActivity extends AppCompatActivity {

    private ExamsCalendarContract.Presenter mExamsPresenter;
    private ExamsCalendarFragment mExamsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams_calendar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LayoutUtils.getNavigationDrawer(ExamsCalendarActivity.this, 2, toolbar);

        mExamsFragment = (ExamsCalendarFragment) getSupportFragmentManager().findFragmentById(R.id.examsCalendarContentFrame);
        if (mExamsFragment == null) {
            mExamsFragment = ExamsCalendarFragment.newInstance();
            LayoutUtils.addFragmentToActivity(getSupportFragmentManager(), mExamsFragment, R.id.examsCalendarContentFrame);
        }

        ExamsRepository repository = Injection.proviceExamsRepository(getApplicationContext());
        ExamsLoader examsLoader = new ExamsLoader(getApplicationContext(), repository);

        mExamsPresenter = new ExamsCalendarPresenter(
                examsLoader,
                getSupportLoaderManager(),
                repository,
                mExamsFragment
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exams_calendar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        switch (itemThatWasClickedId) {
            case R.id.action_refresh_exams: {
                mExamsPresenter.loadExams(true);
                return true;
            }
            case R.id.action_calendar_settings: {
                Intent intent = new Intent(this, CalendarSettingsActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);

    }
}
