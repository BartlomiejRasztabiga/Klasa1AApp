package pl.rasztabiga.klasa1a.mainAct;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import de.cketti.library.changelog.ChangeLog;
import pl.rasztabiga.klasa1a.Injection;
import pl.rasztabiga.klasa1a.R;
import pl.rasztabiga.klasa1a.data.source.luckyNumbers.LuckyNumbersLoader;
import pl.rasztabiga.klasa1a.data.source.luckyNumbers.LuckyNumbersRepository;
import pl.rasztabiga.klasa1a.data.source.onDuties.OnDutiesLoader;
import pl.rasztabiga.klasa1a.data.source.onDuties.OnDutiesRepository;
import pl.rasztabiga.klasa1a.updater.UpdaterPresenter;
import pl.rasztabiga.klasa1a.utils.LayoutUtils;
import pl.rasztabiga.klasa1a.utils.PreferencesUtils;


public class MainActivity extends AppCompatActivity {

    private OnDutiesPresenter mOnDutiesPresenter;
    private LuckyNumbersPresenter mLuckyNumbersPresenter;
    private UpdaterPresenter mUpdaterPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LayoutUtils.getNavigationDrawer(MainActivity.this, 1, toolbar);

        //show changelog on first run
        showChangelogOnFirstRun();

        //lookForNewUpdate

        //OnDuties
        OnDutiesFragment onDutiesFragment = (OnDutiesFragment) getSupportFragmentManager().findFragmentById(R.id.onDutiesContentFrame);
        if (onDutiesFragment == null) {
            onDutiesFragment = OnDutiesFragment.newInstance();
            LayoutUtils.addFragmentToActivity(getSupportFragmentManager(), onDutiesFragment, R.id.onDutiesContentFrame);
        }

        //Create the presenter
        OnDutiesRepository onDutiesRepository = Injection.provideOnDutiesRepository(getApplicationContext());
        OnDutiesLoader onDutiesLoader = new OnDutiesLoader(getApplicationContext(), onDutiesRepository);

        mOnDutiesPresenter = new OnDutiesPresenter(
                onDutiesLoader,
                getSupportLoaderManager(),
                onDutiesRepository,
                onDutiesFragment
        );

        //LuckyNumbers
        LuckyNumbersFragment luckyNumbersFragment = (LuckyNumbersFragment) getSupportFragmentManager().findFragmentById(R.id.luckyNumbersContentFrame);
        if (luckyNumbersFragment == null) {
            luckyNumbersFragment = LuckyNumbersFragment.newInstance();
            LayoutUtils.addFragmentToActivity(getSupportFragmentManager(), luckyNumbersFragment, R.id.luckyNumbersContentFrame);
        }

        LuckyNumbersRepository luckyNumbersRepository = Injection.provideLuckyNumbersRepository(getApplicationContext());
        LuckyNumbersLoader luckyNumbersLoader = new LuckyNumbersLoader(getApplicationContext(), luckyNumbersRepository);

        mLuckyNumbersPresenter = new LuckyNumbersPresenter(
                luckyNumbersLoader,
                getSupportLoaderManager(),
                luckyNumbersRepository,
                luckyNumbersFragment
        );

        //Updater
        mUpdaterPresenter = new UpdaterPresenter(
                getSupportLoaderManager(),
                this,
                getApplicationContext()
        );

        //mUpdaterPresenter.showNewVersionDialog();
        mUpdaterPresenter.checkNewVersion();


    }

    private void showChangelogOnFirstRun() {
        ChangeLog changelog = new ChangeLog(this);
        if (changelog.isFirstRun()) {
            changelog.getLogDialog().show();
        }
    }

    private void showChangelog() {
        ChangeLog changelog = new ChangeLog(this);
        changelog.getFullLogDialog().show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        switch (itemThatWasClickedId) {
            case R.id.action_refresh: {
                mOnDutiesPresenter.loadOnDuties(true);
                mLuckyNumbersPresenter.loadLuckyNumbers(true);
                return true;
            }
            case R.id.action_reset_apikey: {
                PreferencesUtils.saveApiKey(getApplicationContext(), "");
            }
        }
        return super.onOptionsItemSelected(item);

    }
}
