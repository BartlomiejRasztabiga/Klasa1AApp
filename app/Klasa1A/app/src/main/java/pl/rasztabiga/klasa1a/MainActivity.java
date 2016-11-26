package pl.rasztabiga.klasa1a;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import pl.rasztabiga.klasa1a.models.Dyzurni;
import pl.rasztabiga.klasa1a.models.Student;
import pl.rasztabiga.klasa1a.utils.NetworkUtilities;


public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getName();

    private TextView name1;
    private TextView name2;

    private TextView errorMessageTextView;

    private ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name1 = (TextView) findViewById(R.id.name1);
        name2 = (TextView) findViewById(R.id.name2);
        errorMessageTextView = (TextView) findViewById(R.id.error_message_display);
        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);

        new GetDyzurniTask().execute();
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showOnDutiesDataView() {
        errorMessageTextView.setVisibility(View.INVISIBLE);
        name1.setVisibility(View.VISIBLE);
        name2.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        name1.setVisibility(View.INVISIBLE);
        name2.setVisibility(View.INVISIBLE);
        errorMessageTextView.setVisibility(View.VISIBLE);
    }

    private void setDyzurni(String JSONString) {
        try {
            JSONObject json = new JSONObject(JSONString);
            JSONObject dyzurny1 = json.getJSONObject("dyzurny1");
            JSONObject dyzurny2 = json.getJSONObject("dyzurny2");

            //TODO I don't know if it's really needed (below)
            Dyzurni dyzurni = new Dyzurni(new Student(dyzurny1.getString("name"), dyzurny1.getString("surname"), dyzurny1.getInt("number")), new Student(dyzurny2.getString("name"), dyzurny2.getString("surname"), dyzurny2.getInt("number")));
            name1.setText(dyzurni.getDyzurny1().getName() + " " + dyzurni.getDyzurny1().getSurname());
            name2.setText(dyzurni.getDyzurny2().getName() + " " + dyzurni.getDyzurny2().getSurname());

        } catch (JSONException e) {
            e.printStackTrace();
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
            if(s != null && !s.equals("")) {
                showOnDutiesDataView();
                setDyzurni(s);
            } else {
                showErrorMessage();
            }
        }
    }

}
