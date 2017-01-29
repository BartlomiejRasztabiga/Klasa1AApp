package pl.rasztabiga.klasa1a.splashAct;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import pl.rasztabiga.klasa1a.R;
import pl.rasztabiga.klasa1a.utils.LayoutUtils;


public class EnterApiKeyActivity extends AppCompatActivity {

    private EnterApiKeyPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_enter_api_key);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d("LOL", "EnterApiKeyActivity");

        EnterApiKeyFragment enterApiKeyFragment = (EnterApiKeyFragment) getSupportFragmentManager().findFragmentById(R.id.enterApiKeyContentFrame);
        if (enterApiKeyFragment == null) {
            enterApiKeyFragment = EnterApiKeyFragment.newInstance();
            LayoutUtils.addFragmentToActivity(getSupportFragmentManager(), enterApiKeyFragment, R.id.enterApiKeyContentFrame);
        }

        mPresenter = new EnterApiKeyPresenter(
                getSupportLoaderManager(),
                enterApiKeyFragment,
                getApplicationContext()
        );

    }
}
