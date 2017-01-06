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
import android.support.v4.app.ShareCompat;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.FileProvider;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

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

import de.cketti.library.changelog.ChangeLog;
import pl.rasztabiga.klasa1a.models.Dyzurni;
import pl.rasztabiga.klasa1a.models.Student;
import pl.rasztabiga.klasa1a.utils.NetworkUtilities;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    //TODO To have numbers and days in one row use linear layout

    private static final int GET_DYZURNI_LOADER = 11;
    private static final int GET_LUCKY_NUMBERS_LOADER = 22;
    private static final int SET_CHANGING_ROOM_STATUS_LOADER = 33;
    private static final String APK_QUERY_URL = "http://rasztabiga.ct8.pl/klasa1a";

    private final String TAG = MainActivity.class.getName();

    private TextView name1;
    private TextView name2;

    private TextView monday_tv;
    private TextView tuesday_tv;
    private TextView wednesday_tv;
    private TextView thursday_tv;
    private TextView friday_tv;

    private TextView errorMessageTextView;

    private ProgressBar loadingIndicator;

    private ChangeLog changeLog;

    private SharedPreferences preferences;
    private String apiKey;

    private ToggleButton changingRoomButton;
    private ToggleButton doorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name1 = (TextView) findViewById(R.id.name1);
        name2 = (TextView) findViewById(R.id.name2);
        monday_tv = (TextView) findViewById(R.id.monday_tv);
        tuesday_tv = (TextView) findViewById(R.id.tuesday_tv);
        wednesday_tv = (TextView) findViewById(R.id.wednesday_tv);
        thursday_tv = (TextView) findViewById(R.id.thursday_tv);
        friday_tv = (TextView) findViewById(R.id.friday_tv);
        errorMessageTextView = (TextView) findViewById(R.id.error_message_display);
        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);

        changeLog = new ChangeLog(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        apiKey = preferences.getString(getString(R.string.apiKey_pref_key), "");

        changingRoomButton = (ToggleButton) findViewById(R.id.changingRoomToogleButton);
        changingRoomButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new SetChangingRoomStatus().execute(0);
                } else {
                    new SetChangingRoomStatus().execute(1);
                }
            }
        });
        doorButton = (ToggleButton) findViewById(R.id.doorToggleButton);

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
            /** FEATURE */
            new GetChangingRoomStatus().execute();

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

    public boolean checkFirstRun() {
        return preferences.getBoolean("isFirstRun", true);

    }

    public void reloadApiKey() {
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
                            loadingIndicator.setVisibility(View.VISIBLE);
                            forceLoad();
                        }
                    }

                    @Override
                    public String loadInBackground() {
                        reloadApiKey();
                        try {
                            return NetworkUtilities.getDyzurni(apiKey);
                        } catch (RequestException e) {
                            return null;
                        }
                    }

                    @Override
                    public void deliverResult(String data) {
                        dyzurniJson = data;
                        loadingIndicator.setVisibility(View.INVISIBLE);
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
                            loadingIndicator.setVisibility(View.VISIBLE);
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
                            return null;
                        }
                    }

                    @Override
                    public void deliverResult(String data) {
                        //Log.d(TAG, "GET_LUCKY_NUMBERS_LOADER:deliverResult()");
                        luckyNumbersString = data;
                        loadingIndicator.setVisibility(View.INVISIBLE);
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
                loadingIndicator.setVisibility(View.INVISIBLE);
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
                loadingIndicator.setVisibility(View.INVISIBLE);
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
                new GetChangingRoomStatus().execute();
                return true;
            }
            case R.id.action_calendar: {
                Intent newIntent = new Intent(this, ExamsCalendarActivity.class);
                startActivity(newIntent);
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

    private void openWebsiteWithApkToDownload(int serverVersionCode) {
        String url = APK_QUERY_URL + serverVersionCode + ".apk";
        Uri uri = Uri.parse(url);

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void showOnDutiesDataView() {
        errorMessageTextView.setVisibility(View.INVISIBLE);
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
        errorMessageTextView.setVisibility(View.VISIBLE);
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
            e.printStackTrace();
        }
    }

    /**
     * FEATURE
     */

    private void setChangingRoomButton(String data) {
        if (data.equals("1")) {
            changingRoomButton.setChecked(true);
        } else {
            changingRoomButton.setChecked(false);
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

            } catch (RequestException e) {
                e.printStackTrace();
            } catch (Exception e) {
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
                return null;
            }
        }
    }

    private class GetChangingRoomStatus extends AsyncTask<Void, Void, String> {
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
            }
            String url = APK_QUERY_URL;
            url += serverVersionCode;
            url += ".apk";

            return url;
        }


        private Uri getDownloadedApkUri(File apkFile) {
            return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) ?
                    FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", apkFile) :
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
