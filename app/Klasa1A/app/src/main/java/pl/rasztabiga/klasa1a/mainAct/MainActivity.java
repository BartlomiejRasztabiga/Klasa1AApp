package pl.rasztabiga.klasa1a.mainAct;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import pl.rasztabiga.klasa1a.R;
import pl.rasztabiga.klasa1a.utils.ActivityUtils;
import pl.rasztabiga.klasa1a.utils.LayoutUtils;


public class MainActivity extends AppCompatActivity {

    //private OnDutiesPresenter mOnDutiesPresenter;

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
    }
}
