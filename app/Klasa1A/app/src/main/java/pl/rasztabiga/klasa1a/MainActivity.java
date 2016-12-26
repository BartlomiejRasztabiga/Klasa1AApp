package pl.rasztabiga.klasa1a;

import android.Manifest;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import de.cketti.library.changelog.ChangeLog;
import pl.rasztabiga.klasa1a.models.Dyzurni;
import pl.rasztabiga.klasa1a.models.LuckyNumbers;
import pl.rasztabiga.klasa1a.models.Student;
import pl.rasztabiga.klasa1a.utils.NetworkUtilities;


public class MainActivity extends AppCompatActivity {

    //TODO To have numbers and days in one row use linear layout

    private static final String DYZURNI_ARRAYLIST_KEY = "dyzurni_arraylist";
    private static final String LUCKY_NUMBERS_ARRAYLIST_KEY = "luckynumbers_arraylist";

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

        if (savedInstanceState != null) {
            Log.d(TAG, "RETRIEVING SAVED STATE");
            if (savedInstanceState.containsKey(DYZURNI_ARRAYLIST_KEY) && savedInstanceState.containsKey(LUCKY_NUMBERS_ARRAYLIST_KEY)) {
                ArrayList<String> dyzurniArrayList = savedInstanceState.getStringArrayList(DYZURNI_ARRAYLIST_KEY);
                if (dyzurniArrayList != null && !dyzurniArrayList.isEmpty()) {
                    name1.setText(dyzurniArrayList.get(0));
                    name2.setText(dyzurniArrayList.get(1));
                }

                ArrayList<String> luckyNumbersArrayList = savedInstanceState.getStringArrayList(LUCKY_NUMBERS_ARRAYLIST_KEY);
                if(luckyNumbersArrayList != null && !luckyNumbersArrayList.isEmpty()) {
                    monday_tv.setText(luckyNumbersArrayList.get(0));
                    tuesday_tv.setText(luckyNumbersArrayList.get(1));
                    wednesday_tv.setText(luckyNumbersArrayList.get(2));
                    thursday_tv.setText(luckyNumbersArrayList.get(3));
                    friday_tv.setText(luckyNumbersArrayList.get(4));
                }
            }
        } else {
            Log.d(TAG, "EXECUTING NETWORK TASKS");
            new GetDyzurniTask().execute();
            new GetLuckyNumbersTask().execute();
        }

        ChangeLog cl = new ChangeLog(this);
        if (cl.isFirstRun()) {
            cl.getLogDialog().show();
        }

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
        if (itemThatWasClickedId == R.id.action_refresh) {
            new GetDyzurniTask().execute();
            new GetLuckyNumbersTask().execute();
            return true;
        } else if (itemThatWasClickedId == R.id.action_calendar) {
            Intent newIntent = new Intent(this, TestsCalendarActivity.class);
            startActivity(newIntent);
        } else if (itemThatWasClickedId == R.id.action_download_app_manually) {
            int serverVersionCode = 0;
            try {
                serverVersionCode = new GetActualAppVersionFromServer().execute().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            String url = "http://rasztabiga.ct8.pl/klasa1a" + serverVersionCode + ".apk";
            Uri uri = Uri.parse(url);

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
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

            //Only for tests
            name1.setText("");
            name2.setText("");
            //End

            //TODO I don't know if it's really needed (below)
            Dyzurni dyzurni = new Dyzurni(new Student(dyzurny1.getString("name"), dyzurny1.getString("surname"), dyzurny1.getInt("number")), new Student(dyzurny2.getString("name"), dyzurny2.getString("surname"), dyzurny2.getInt("number")));
            name1.setText(dyzurni.getDyzurny1().getName() + " " + dyzurni.getDyzurny1().getSurname());
            name2.setText(dyzurni.getDyzurny2().getName() + " " + dyzurni.getDyzurny2().getSurname());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setLuckyNumbers(String JSONString) {
        try {
            JSONObject json = new JSONObject(JSONString);
            JSONArray list = json.getJSONArray("numbersList");
            ArrayList<Integer> arrayList = new ArrayList<>(5);

            for (int i = 0; i < list.length(); i++) {
                arrayList.add(Integer.valueOf(list.get(i).toString()));
            }

            LuckyNumbers luckyNumbers = new LuckyNumbers(arrayList);
            ArrayList<Integer> luckyNumbersList = luckyNumbers.getNumbersList();

            //Only for tests
            monday_tv.setText("");
            tuesday_tv.setText("");
            wednesday_tv.setText("");
            thursday_tv.setText("");
            friday_tv.setText("");
            //End

            if (luckyNumbersList.get(0) != 0) {
                monday_tv.setText(String.valueOf(luckyNumbersList.get(0)));
                tuesday_tv.setText(String.valueOf(luckyNumbersList.get(1)));
                wednesday_tv.setText(String.valueOf(luckyNumbersList.get(2)));
                thursday_tv.setText(String.valueOf(luckyNumbersList.get(3)));
                friday_tv.setText(String.valueOf(luckyNumbersList.get(4)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

        ArrayList<String> dyzurniArrayList = new ArrayList<>(Arrays.asList(name1.getText().toString(), name2.getText().toString()));
        outState.putStringArrayList(DYZURNI_ARRAYLIST_KEY, dyzurniArrayList);

        ArrayList<String> luckyNumbersArrayList = new ArrayList<>(Arrays.asList(monday_tv.getText().toString(), tuesday_tv.getText().toString(),
                wednesday_tv.getText().toString(), thursday_tv.getText().toString(), friday_tv.getText().toString()));
        outState.putStringArrayList(LUCKY_NUMBERS_ARRAYLIST_KEY, luckyNumbersArrayList);

    }

    private class GetDyzurniTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return NetworkUtilities.getDyzurni();
        }

        @Override
        protected void onPostExecute(String s) {
            loadingIndicator.setVisibility(View.INVISIBLE);
            if (s != null && !s.equals("")) {
                showOnDutiesDataView();
                setDyzurni(s);
            } else {
                showErrorMessage();
            }
        }
    }

    private class GetLuckyNumbersTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return NetworkUtilities.getLuckyNumbers();
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {
                showOnDutiesDataView();
                setLuckyNumbers(s);
            }
        }
    }

    private class CheckNewUpdatesTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                int installedVersionCode = pInfo.versionCode;
                int serverVersionCode = NetworkUtilities.getActualVersion();
                if (installedVersionCode == 0) {
                    throw new Exception("Błędna wersja na serwerze, skontakuj się z administratorem");
                } else if (serverVersionCode > installedVersionCode) {
                    Log.d(TAG, "New version available.\n Yours: " + installedVersionCode + "\n" + "Server: " + serverVersionCode);
                    return true;
                } else {
                    return false;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class GetActualAppVersionFromServer extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... voids) {
            return NetworkUtilities.getActualVersion();
        }
    }

    private class DownloadNewVersion extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            if (!isStoragePermissionGranted()) {
                Log.d(TAG, "You didn't give me permission!");
                return null;
            }

            //get destination to update file and set Uri
            //TODO: First I wanted to store my update .apk file on internal storage for my app but apparently android does not allow you to open and install
            //aplication with existing package from there. So for me, alternative solution is Download directory in external storage. If there is better
            //solution, please inform us in comment
            //TODO CHANGE THIS TO SDK 25, http://stackoverflow.com/questions/38200282/android-os-fileuriexposedexception-file-storage-emulated-0-test-txt-exposed

            File file = new File(Environment.getExternalStorageDirectory(), "klasa1a.apk");
            final Uri uri = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) ?
                    FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", file) :
                    Uri.fromFile(file);

            //Delete update file if exists
            //File file = new File(destination);
            if (file.exists())
                //file.delete() - test this, I think sometimes it doesnt work
                file.delete();

            //get url of app on server
            int serverVersionCode = NetworkUtilities.getActualVersion();
            String url = "http://rasztabiga.ct8.pl/klasa1a";
            url += serverVersionCode;
            url += ".apk";

            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL sUrl = new URL(url);
                connection = (HttpURLConnection) sUrl.openConnection();
                connection.connect();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(file);

                byte data[] = new byte[4096];
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
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

            Intent install = new Intent(Intent.ACTION_INSTALL_PACKAGE)
                    .setData(uri)
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(install);

            return null;
        }


    }


}
