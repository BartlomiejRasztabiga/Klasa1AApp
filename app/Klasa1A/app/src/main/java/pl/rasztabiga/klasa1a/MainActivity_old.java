/*
package pl.rasztabiga.klasa1a;

import android.Manifest;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.FileProvider;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.cketti.library.changelog.ChangeLog;
import pl.rasztabiga.klasa1a.data.source.models.Dyzurni;
import pl.rasztabiga.klasa1a.data.source.models.Student;
import pl.rasztabiga.klasa1a.utils.FirebaseUtils;
import pl.rasztabiga.klasa1a.utils.LayoutUtils;
import pl.rasztabiga.klasa1a.utils.NetworkUtilities;


public class MainActivity_old extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    //TODO To have numbers and days in one row use linear layout
    private static final int GET_DYZURNI_LOADER = 11;

    private static final int GET_LUCKY_NUMBERS_LOADER = 22;
    private static final String APK_QUERY_URL = "https://klasa1a-app.firebaseapp.com/klasa1a";

    private final String TAG = MainActivity_old.class.getName();

    @BindView(R.id.name1)
    TextView name1;

    @BindView(R.id.name2)
    TextView name2;

    @BindView(R.id.monday_tv)
    TextView monday_tv;
    @BindView(R.id.tuesday_tv)
    TextView tuesday_tv;
    @BindView(R.id.wednesday_tv)
    TextView wednesday_tv;
    @BindView(R.id.thursday_tv)
    TextView thursday_tv;
    @BindView(R.id.friday_tv)
    TextView friday_tv;


    @BindView(R.id.changingRoomToggleButton)
    ToggleButton changingRoomButton;
    @BindView(R.id.doorToggleButton)
    ToggleButton doorButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String apiKey;
    private ChangeLog changeLog;
    private SharedPreferences preferences;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        LayoutUtils.getNavigationDrawer(MainActivity_old.this, 1, toolbar);
        //setSupportActionBar(toolbar);

        changeLog = new ChangeLog(this);
        LayoutUtils.setMainActivityRef(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        apiKey = preferences.getString(getString(R.string.apiKey_pref_key), "");

        FirebaseDatabase database = FirebaseUtils.getDatabase();
        final DatabaseReference changingRoomStatusRef = database.getReference("changingRoomStatus");
        final DatabaseReference doorStatusRef = database.getReference("doorStatus");

        changingRoomStatusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean value = dataSnapshot.getValue(Boolean.class);
                //Log.d(TAG, "value: " + value);
                changingRoomButton.setChecked(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "error getting value from firebase db");
            }
        });

        doorStatusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean value = dataSnapshot.getValue(Boolean.class);
                //Log.d(TAG, "value: " + value);
                doorButton.setChecked(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "error getting value from firebase db");
            }
        });

        changingRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleButton toggleButton = (ToggleButton) v;
                if (toggleButton.isChecked()) {
                    //new SetChangingRoomStatus().execute(1);
                    changingRoomStatusRef.setValue(true);
                } else {
                    //new SetChangingRoomStatus().execute(0);
                    changingRoomStatusRef.setValue(false);
                }
            }
        });
        doorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleButton toggleButton = (ToggleButton) v;
                if (toggleButton.isChecked()) {
                    //new SetDoorStatus().execute(1);
                    doorStatusRef.setValue(true);
                } else {
                    //new SetDoorStatus().execute(0);
                    doorStatusRef.setValue(false);
                }
            }
        });


        if (checkFirstRun()) {
            showEnterApiKeyDialog();
            apiKey = preferences.getString(getString(R.string.apiKey_pref_key), "");
        }

        if (changeLog.isFirstRun()) {
            changeLog.getLogDialog().show();
        }

        if (!apiKey.isEmpty() || !apiKey.equals("")) {
            getSupportLoaderManager().initLoader(GET_DYZURNI_LOADER, null, this);
            getSupportLoaderManager().initLoader(GET_LUCKY_NUMBERS_LOADER, null, this);
            */
/**
 * FEATURE
 * FEATURE
 * <p>
 * CHANGINGROOM AND DOOR STATUS
 * <p>
 * FEATURE
 * <p>
 * CHANGINGROOM AND DOOR STATUS
 *//*

            //new GetChangingRoomStatus().execute();
            //new GetDoorStatus().execute();

            try {
                if (new CheckNewUpdatesTask().execute().get()) {
                    Log.v(TAG, "New version!");
                    //TODO Dodać pytanie użytkownika czy pobrać
                    if (isStoragePermissionGranted()) {
                        showDownloadNewVersionDialog();
                    } else {
                        Log.w(TAG, "You didn't give me permission!");
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }


    }

    private boolean checkFirstRun() {
        return preferences.getBoolean("isFirstRun", true);

    }

    private void reloadApiKey() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        apiKey = preferences.getString(getString(R.string.apiKey_pref_key), "");
    }

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        switch (id) {
            case GET_DYZURNI_LOADER: {
                return new AsyncTaskLoader<String>(this) {
                    String dyzurniJson = null;

                    @Override
                    protected void onStartLoading() {

                        if (dyzurniJson != null) {
                            deliverResult(dyzurniJson);
                        } else {
                            forceLoad();
                        }
                    }

                    @Override
                    public String loadInBackground() {
                        reloadApiKey();
                        try {
                            return NetworkUtilities.getDyzurni(apiKey);
                        } catch (RequestException e) {
                            FirebaseCrash.logcat(Log.ERROR, TAG, "RequestException caught");
                            FirebaseCrash.report(e);
                            return null;
                        }
                    }

                    @Override
                    public void deliverResult(String data) {
                        dyzurniJson = data;
                        super.deliverResult(data);
                    }
                };
            }

            case GET_LUCKY_NUMBERS_LOADER: {
                return new AsyncTaskLoader<String>(this) {
                    String luckyNumbersString = null;

                    @Override
                    protected void onStartLoading() {
                        //Log.d(TAG, "GET_LUCKY_NUMBERS_LOADER:onStartLoading()");
                        if (luckyNumbersString != null) {
                            deliverResult(luckyNumbersString);
                        } else {
                            // added visibility
                            forceLoad();
                        }
                    }

                    @Override
                    public String loadInBackground() {
                        reloadApiKey();
                        //Log.d(TAG, "GET_LUCKY_NUMBERS_LOADER:loadInBackground()");
                        try {
                            return NetworkUtilities.getLuckyNumbers(apiKey);
                        } catch (RequestException e) {
                            FirebaseCrash.logcat(Log.ERROR, TAG, "RequestException caught");
                            FirebaseCrash.report(e);
                            return null;
                        }
                    }

                    @Override
                    public void deliverResult(String data) {
                        //Log.d(TAG, "GET_LUCKY_NUMBERS_LOADER:deliverResult()");
                        luckyNumbersString = data;
                        super.deliverResult(data);
                    }
                };
            }

        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        switch (loader.getId()) {
            case GET_DYZURNI_LOADER: {
                if (data != null && !data.equals("")) {
                    showOnDutiesDataView();
                    setDyzurni(data);
                } else {
                    showErrorMessage();
                }
                break;
            }

            case GET_LUCKY_NUMBERS_LOADER: {
                //Log.d(TAG, "GET_LUCKY_NUMBERS_LOADER:onLoadFinished()");
                if (data != null && !data.equals("")) {
                    showOnDutiesDataView();
                    setLuckyNumbers(data);
                }
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }

    private void showEnterApiKeyDialog() {
        DialogFragment dialogFragment = new EnterApiKeyDialog();
        dialogFragment.show(getFragmentManager(), "EnterApiKeyDialog");
    }

    private void showDownloadNewVersionDialog() {
        DialogFragment dialog = new DownloadNewVersionDialog();
        dialog.show(getFragmentManager(), "DownloadNewVersionDialog");
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
                resetDyzurniTextViews();
                resetLuckyNumbersTextViews();
                getSupportLoaderManager().restartLoader(GET_DYZURNI_LOADER, null, this);
                getSupportLoaderManager().restartLoader(GET_LUCKY_NUMBERS_LOADER, null, this);
                return true;
            }
            case R.id.action_download_app_manually: {
                int serverVersionCode = 0;
                try {
                    serverVersionCode = new GetActualAppVersionFromServer().execute().get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                openWebsiteWithApkToDownload(serverVersionCode);

                return true;
            }
            case R.id.action_show_changelog: {
                changeLog.getFullLogDialog().show();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);

    }

    public void openWebsiteWithApkToDownload(int serverVersionCode) {
        String url = APK_QUERY_URL + serverVersionCode + ".apk";
        Uri uri = Uri.parse(url);

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void showOnDutiesDataView() {
        name1.setVisibility(View.VISIBLE);
        name2.setVisibility(View.VISIBLE);
        monday_tv.setVisibility(View.VISIBLE);
        tuesday_tv.setVisibility(View.VISIBLE);
        wednesday_tv.setVisibility(View.VISIBLE);
        thursday_tv.setVisibility(View.VISIBLE);
        friday_tv.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        name1.setVisibility(View.INVISIBLE);
        name2.setVisibility(View.INVISIBLE);
        monday_tv.setVisibility(View.INVISIBLE);
        tuesday_tv.setVisibility(View.INVISIBLE);
        wednesday_tv.setVisibility(View.INVISIBLE);
        thursday_tv.setVisibility(View.INVISIBLE);
        friday_tv.setVisibility(View.INVISIBLE);
    }

    private void setDyzurni(String JSONString) {
        try {
            JSONObject json = new JSONObject(JSONString);
            JSONObject dyzurny1 = json.getJSONObject("dyzurny1");
            JSONObject dyzurny2 = json.getJSONObject("dyzurny2");

            resetDyzurniTextViews();
            //TODO I don't know if it's really needed (below)
            Dyzurni dyzurni = new Dyzurni(new Student(dyzurny1.getString("name"), dyzurny1.getString("surname"), dyzurny1.getInt("number")), new Student(dyzurny2.getString("name"), dyzurny2.getString("surname"), dyzurny2.getInt("number")));
            name1.setText(dyzurni.getDyzurny1().getName() + " " + dyzurni.getDyzurny1().getSurname());
            name2.setText(dyzurni.getDyzurny2().getName() + " " + dyzurni.getDyzurny2().getSurname());

        } catch (JSONException e) {
            FirebaseCrash.logcat(Log.ERROR, TAG, "JSONException caught");
            FirebaseCrash.report(e);
            e.printStackTrace();
        }
    }

    */
/**
 * FEATURE
 *//*


    private void setChangingRoomButton(String data) {
        if (data.equals("1")) {
            changingRoomButton.setChecked(true);
        } else {
            changingRoomButton.setChecked(false);
        }

    }

    private void setDoorButton(String data) {
        if (data.equals("1")) {
            doorButton.setChecked(true);
        } else {
            doorButton.setChecked(false);
        }

    }


    private void resetDyzurniTextViews() {
        name1.setText("");
        name2.setText("");
    }

    private void setLuckyNumbers(String JSONString) {
        try {
            JSONObject json = new JSONObject(JSONString);
            JSONArray list = json.getJSONArray("numbersList");
            ArrayList<Integer> luckyNumbersList = new ArrayList<>(5);

            for (int i = 0; i < list.length(); i++) {
                luckyNumbersList.add(Integer.valueOf(list.get(i).toString()));
            }

            resetLuckyNumbersTextViews();

            if (luckyNumbersList.get(0) != 0) {
                setLuckyNumbersTextViews(luckyNumbersList);
            }

        } catch (JSONException e) {
            FirebaseCrash.logcat(Log.ERROR, TAG, "JSONException caught");
            FirebaseCrash.report(e);
            e.printStackTrace();
        }
    }

    private void setLuckyNumbersTextViews(ArrayList<Integer> luckyNumbersList) {
        monday_tv.setText(String.valueOf(luckyNumbersList.get(0)));
        tuesday_tv.setText(String.valueOf(luckyNumbersList.get(1)));
        wednesday_tv.setText(String.valueOf(luckyNumbersList.get(2)));
        thursday_tv.setText(String.valueOf(luckyNumbersList.get(3)));
        friday_tv.setText(String.valueOf(luckyNumbersList.get(4)));
    }

    private void resetLuckyNumbersTextViews() {
        monday_tv.setText("");
        tuesday_tv.setText("");
        wednesday_tv.setText("");
        thursday_tv.setText("");
        friday_tv.setText("");
    }

    void downloadNewVersion() {
        new DownloadNewVersion().execute();
    }

    private boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    public int getActualAppVersion() {
        try {
            return new GetActualAppVersionFromServer().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }

    private class CheckNewUpdatesTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                reloadApiKey();
                PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                int installedVersionCode = pInfo.versionCode;
                int serverVersionCode = NetworkUtilities.getActualVersion(apiKey);
                if (installedVersionCode == 0) {
                    throw new Exception("Błędna wersja na serwerze, skontakuj się z administratorem");
                } else if (serverVersionCode > installedVersionCode) {
                    Log.d(TAG, "New version available.\n Yours: " + installedVersionCode + "\n" + "Server: " + serverVersionCode);
                    return true;
                } else {
                    return false;
                }

            } catch (Exception e) {
                FirebaseCrash.logcat(Log.ERROR, TAG, "Exception caught");
                FirebaseCrash.report(e);
                e.printStackTrace();
            }

            return null;
        }
    }

    private class GetActualAppVersionFromServer extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... voids) {
            reloadApiKey();
            try {
                return NetworkUtilities.getActualVersion(apiKey);
            } catch (RequestException e) {
                FirebaseCrash.logcat(Log.ERROR, TAG, "RequestException caught");
                FirebaseCrash.report(e);
                return null;
            }
        }
    }

    */
/**
 * CHANGINGROOM AND DOOR STATUS
 *//*


    */
/*private class GetChangingRoomStatus extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                return NetworkUtilities.getChangingRoomStatus(apiKey);
            } catch (RequestException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null && !s.equals("")) {
                setChangingRoomButton(s);
            }
        }
    }

    private class SetChangingRoomStatus extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... params) {
            try {
                NetworkUtilities.setChangingRoomStatus(apiKey, params[0]);
            } catch (RequestException e) {
                return null;
            }
            return null;
        }
    }
    private class GetDoorStatus extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                return NetworkUtilities.getDoorStatus(apiKey);
            } catch (RequestException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null && !s.equals("")) {
                setDoorButton(s);
            }
        }
    }

    private class SetDoorStatus extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... params) {
            try {
                NetworkUtilities.setDoorStatus(apiKey, params[0]);
            } catch (RequestException e) {
                return null;
            }
            return null;
        }
    }*//*


    private class DownloadNewVersion extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            reloadApiKey();
            if (!isStoragePermissionGranted()) {
                Log.d(TAG, "You didn't give me permission!");
                return null;
            }

            File file = new File(Environment.getExternalStorageDirectory(), "klasa1a.apk");
            final Uri uri = getDownloadedApkUri(file);

            if (file.exists()) {
                file.delete();
            }

            downloadApk(file);
            installApk(uri);

            return null;
        }

        private void downloadApk(File apkFile) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL sUrl = new URL(getApkUrl());
                connection = (HttpURLConnection) sUrl.openConnection();
                connection.connect();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(apkFile);

                byte data[] = new byte[4096];
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return;
                    }

                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                FirebaseCrash.logcat(Log.ERROR, TAG, "Exception caught");
                FirebaseCrash.report(e);
                e.printStackTrace();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
        }

        private String getApkUrl() {
            int serverVersionCode = 0;
            try {
                serverVersionCode = NetworkUtilities.getActualVersion(apiKey);
            } catch (RequestException e) {
                FirebaseCrash.logcat(Log.ERROR, TAG, "RequestException caught");
                FirebaseCrash.report(e);
                e.printStackTrace();
            }
            String url = APK_QUERY_URL;
            url += serverVersionCode;
            url += ".apk";

            return url;
        }


        private Uri getDownloadedApkUri(File apkFile) {
            return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) ?
                    FileProvider.getUriForFile(MainActivity_old.this, BuildConfig.APPLICATION_ID + ".provider", apkFile) :
                    Uri.fromFile(apkFile);
        }

        private void installApk(Uri apkUri) {
            Intent install = new Intent(Intent.ACTION_INSTALL_PACKAGE)
                    .setData(apkUri)
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(install);
        }

    }


}
*/
