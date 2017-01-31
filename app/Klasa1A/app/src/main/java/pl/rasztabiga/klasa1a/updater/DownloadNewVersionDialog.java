package pl.rasztabiga.klasa1a.updater;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import pl.rasztabiga.klasa1a.R;


public class DownloadNewVersionDialog extends DialogFragment {

    private final String TAG = DownloadNewVersionDialog.class.getName();
    private UpdaterPresenter mPresenter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_download_new_version)
                .setPositiveButton(R.string.download, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.downloadNewVersion();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }

    public void setPresenter(UpdaterPresenter presenter) {
        this.mPresenter = presenter;
    }
}
