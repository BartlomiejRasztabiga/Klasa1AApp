package pl.rasztabiga.klasa1a;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import pl.rasztabiga.klasa1a.utils.NetworkUtilities;

public class EnterApiKeyDialog extends DialogFragment {
    private final String TAG = MainActivity.class.getName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.api_key_dialog, null);
        builder.setView(dialogView);


        builder.setPositiveButton(R.string.continue_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODo Do nothing
            }
        });

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        final AlertDialog d = (AlertDialog) getDialog();
        final EditText editText = (EditText) d.findViewById(R.id.api_key);
        final TextView error_text_view = (TextView) d.findViewById(R.id.error_text_view);
        if (d != null) {
            Button positiveButton = d.getButton(DialogInterface.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean wantToCloseDialog = false;

                    try {
                        error_text_view.setText(new CheckApiKeyTask().execute(editText.getText().toString()).get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }

                    if (error_text_view.getText().toString().equals("") || error_text_view.getText().toString().isEmpty()) {
                        wantToCloseDialog = true;
                    }

                    if (wantToCloseDialog)
                        d.dismiss();

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    preferences.edit().putBoolean(getString(R.string.isFirstRun_pref_key), false).apply();
                    preferences.edit().putString(getString(R.string.apiKey_pref_key), editText.getText().toString()).apply();
                }
            });
        }
    }

    private class CheckApiKeyTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return NetworkUtilities.checkApikey(params[0]);
        }
    }
}


