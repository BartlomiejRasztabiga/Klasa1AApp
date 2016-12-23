package pl.rasztabiga.klasa1a;

import android.Manifest;
import android.app.DialogFragment;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import de.cketti.library.changelog.ChangeLog;
import pl.rasztabiga.klasa1a.models.Dyzurni;
import pl.rasztabiga.klasa1a.models.LuckyNumbers;
import pl.rasztabiga.klasa1a.models.Student;
import pl.rasztabiga.klasa1a.utils.NetworkUtilities;


public class MainActivity extends AppCompatActivity {

    //TODO To have numbers and days in one row use linear layout

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

        new GetDyzurniTask().execute();
        new GetLuckyNumbersTask().execute();

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

            //File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "klasa1a.apk");
            File file = new File(Environment.getExternalStorageDirectory(), "klasa1a.apk");
            final Uri uri = FileProvider.getUriForFile(MainActivity.this, getApplicationContext().getPackageName() + ".provider", file);

            //Delete update file if exists
            //File file = new File(destination);
            if (file.exists())
                //file.delete() - test this, I think sometimes it doesnt work
                file.delete();

            //get url of app on server
            String url = "http://rasztabiga.ct8.pl/klasa1a.apk";

            //set downloadmanager
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setDescription("Downloading new version");
            request.setTitle(MainActivity.this.getString(R.string.app_name));

            //set destination
            request.setDestinationUri(uri);

            // get download service and enqueue file
            final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            final long downloadId = manager.enqueue(request);

            //set BroadcastReceiver to install app when .apk is downloaded
            BroadcastReceiver onComplete = new BroadcastReceiver() {
                public void onReceive(Context ctxt, Intent intent) {
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    install.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    install.setDataAndType(uri,
                            manager.getMimeTypeForDownloadedFile(downloadId));
                    startActivity(install);

                    unregisterReceiver(this);
                    finish();
                }
            };
            //register receiver for when .apk download is compete
            registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

            return null;
        }
    }


}
