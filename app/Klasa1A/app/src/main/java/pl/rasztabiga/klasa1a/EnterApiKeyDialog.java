package pl.rasztabiga.klasa1a;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import pl.rasztabiga.klasa1a.utils.NetworkUtilities;

public class EnterApiKeyDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.api_key_dialog, null);
        builder.setView(dialogView);

        final EditText editText = (EditText) dialogView.findViewById(R.id.api_key);
        final TextView error_text_view = (TextView) dialogView.findViewById(R.id.error_text_view);
        builder.setPositiveButton(R.string.continue_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //error_text_view.setText(NetworkUtilities.checkApikey(editText.getText().toString()));
                //TODO write to preferences apiKey & setFirstRun to false
                //TODo Wyrzucic networking do innego tasku
                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                        .putBoolean("isFirstRun", false).apply();
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
}
