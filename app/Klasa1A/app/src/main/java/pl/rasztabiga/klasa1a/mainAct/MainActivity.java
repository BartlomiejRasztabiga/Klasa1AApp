package pl.rasztabiga.klasa1a.mainAct;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import pl.rasztabiga.klasa1a.Injection;
import pl.rasztabiga.klasa1a.R;
import pl.rasztabiga.klasa1a.data.source.OnDutiesLoader;
import pl.rasztabiga.klasa1a.data.source.OnDutiesRepository;
import pl.rasztabiga.klasa1a.data.source.local.OnDutiesLocalDataSource;
import pl.rasztabiga.klasa1a.data.source.remote.OnDutiesRemoteDataSource;
import pl.rasztabiga.klasa1a.utils.ActivityUtils;
import pl.rasztabiga.klasa1a.utils.LayoutUtils;


public class MainActivity extends AppCompatActivity {

    private OnDutiesPresenter mOnDutiesPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LayoutUtils.getNavigationDrawer(MainActivity.this, 1 ,toolbar);

        Log.d("MainActivity", "Before onDutiesFragment");
        OnDutiesFragment onDutiesFragment = (OnDutiesFragment) getSupportFragmentManager().findFragmentById(R.id.onDutiesContentFrame);
        if(onDutiesFragment == null) {
            onDutiesFragment = OnDutiesFragment.newInstance();
            LayoutUtils.addFragmentToActivity(getSupportFragmentManager(), onDutiesFragment, R.id.onDutiesContentFrame);
        }

        //Create the presenter
        OnDutiesRepository repository = Injection.provideOnDutiesRepository(getApplicationContext());
        OnDutiesLoader onDutiesLoader = new OnDutiesLoader(getApplicationContext(), repository);

        mOnDutiesPresenter = new OnDutiesPresenter(
                onDutiesLoader,
                getSupportLoaderManager(),
                repository,
                onDutiesFragment
        );


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
                return true;
            }
            case R.id.action_download_app_manually: {
                return true;
            }
            case R.id.action_show_changelog: {
                return true;
            }
        }
        return super.onOptionsItemSelected(item);

    }
}
