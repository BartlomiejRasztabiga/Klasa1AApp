package pl.rasztabiga.klasa1a.splashAct;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import pl.rasztabiga.klasa1a.R;
import pl.rasztabiga.klasa1a.mainAct.MainActivity;
import pl.rasztabiga.klasa1a.utils.LayoutUtils;
import pl.rasztabiga.klasa1a.utils.PreferencesUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_splash);


        if (PreferencesUtils.getApiKey(getApplicationContext()).equals("")) {
            /*Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);*/
        } else {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EnterApiKeyFragment enterApiKeyFragment = (EnterApiKeyFragment) getSupportFragmentManager().findFragmentById(R.id.enterApiKeyContentFrame);
        if (enterApiKeyFragment == null) {
            enterApiKeyFragment = EnterApiKeyFragment.newInstance();
            LayoutUtils.addFragmentToActivity(getSupportFragmentManager(), enterApiKeyFragment, R.id.enterApiKeyContentFrame);
        }
    }


}
