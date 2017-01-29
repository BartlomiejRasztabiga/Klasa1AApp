package pl.rasztabiga.klasa1a.splashAct;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import pl.rasztabiga.klasa1a.R;
import pl.rasztabiga.klasa1a.mainAct.MainActivity;
import pl.rasztabiga.klasa1a.utils.LayoutUtils;
import pl.rasztabiga.klasa1a.utils.PreferencesUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.acitivity_enter_api_key);

        PreferencesUtils.saveApiKey(getApplicationContext(), "");

        if (PreferencesUtils.getApiKey(getApplicationContext()).equals("")) {
            Intent intent = new Intent(SplashActivity.this, EnterApiKeyActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


}
