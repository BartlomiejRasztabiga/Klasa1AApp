package pl.rasztabiga.klasa1a.data.source.updater;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import pl.rasztabiga.klasa1a.BuildConfig;
import pl.rasztabiga.klasa1a.RequestException;
import pl.rasztabiga.klasa1a.utils.ApiKeyUtils;
import pl.rasztabiga.klasa1a.utils.NetworkUtilities;

public final class DownloadNewVersionLoader extends AsyncTaskLoader<Void> {

    private static final String TAG = DownloadNewVersionLoader.class.getSimpleName();

    //private static final String APK_QUERY_URL = "https://klasa1a-app.firebaseapp.com/klasa1a";
    private static final String APK_QUERY_URL = "http://94.177.229.18/app/klasa1a";

    private WeakReference<Context> mContext;

    public DownloadNewVersionLoader(Context context) {
        super(context);
        mContext = new WeakReference<>(context);
    }

    @Override
    public Void loadInBackground() {

        File file = new File(Environment.getExternalStorageDirectory(), "klasa1a.apk");
        final Uri uri = getDownloadedApkUri(file);

        if (file.exists()) {
            file.delete();
        }

        downloadApk(file);
        if (file.exists()) {
            Log.d(TAG, "EXISTS!");
            Log.d(TAG, String.valueOf(file.canRead()));
            Log.d(TAG, file.toString());
        }
        installApk(uri);

        return null;
    }

    private Uri getDownloadedApkUri(File apkFile) {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) ?
                FileProvider.getUriForFile(mContext.get(), BuildConfig.APPLICATION_ID + ".provider", apkFile) :
                Uri.fromFile(apkFile);
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

            byte[] data = new byte[4096];
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isAbandoned() && isReset()) {
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
                if (output != null) {
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
    }

    private String getApkUrl() {
        int serverVersionCode = 0;
        try {
            serverVersionCode = NetworkUtilities.getActualVersion(ApiKeyUtils.getApiKey(mContext.get()));
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

    private void installApk(Uri apkUri) {
        Intent install = new Intent(Intent.ACTION_INSTALL_PACKAGE)
                .setData(apkUri)
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        mContext.get().startActivity(install);
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }

}
