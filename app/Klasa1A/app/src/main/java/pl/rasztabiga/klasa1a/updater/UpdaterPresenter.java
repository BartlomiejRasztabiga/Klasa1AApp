package pl.rasztabiga.klasa1a.updater;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.util.Log;

import pl.rasztabiga.klasa1a.data.source.updater.CheckNewUpdatesLoader;
import pl.rasztabiga.klasa1a.data.source.updater.DownloadNewVersionLoader;

import static com.google.common.base.Preconditions.checkNotNull;

public class UpdaterPresenter implements UpdaterContract.Presenter {

    private final static String TAG = UpdaterPresenter.class.getSimpleName();

    private final static int CHECK_NEW_VERSION_QUERY = 5;

    private final static int DOWNLOAD_NEW_VERSION_QUERY = 6;

    private final CheckNewUpdatesLoader mCheckNewUpdatesLoader;

    private final DownloadNewVersionLoader mDownloadNewVersionLoader;

    private final LoaderManager mLoaderManager;

    private final Activity mActivity;

    public UpdaterPresenter(@NonNull LoaderManager loaderManager, @NonNull Activity activity, @NonNull Context context) {
        mCheckNewUpdatesLoader = new CheckNewUpdatesLoader(context);
        mDownloadNewVersionLoader = new DownloadNewVersionLoader(context);
        mLoaderManager = checkNotNull(loaderManager);
        mActivity = checkNotNull(activity);
    }

    @Override
    public void checkNewVersion() {
        CheckNewVersionCallback callback = new CheckNewVersionCallback();
        callback.checkNewVersion();
    }

    @Override
    public void downloadNewVersion() {
        if (!isStoragePermissionGranted()) {
            Log.d(TAG, "You didn't give me permission!");
            return;
        }
        DownloadNewVersionCallback callback = new DownloadNewVersionCallback();
        callback.downloadNewVersion();

        Log.d(TAG, "Downloading New Version");
    }

    private boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (mActivity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    @Override
    public void showNewVersionDialog() {

        DownloadNewVersionDialog dialogFragment = new DownloadNewVersionDialog();
        dialogFragment.setPresenter(this);
        dialogFragment.show(mActivity.getFragmentManager(), "DownloadNewVersionDialog");
    }

    @Override
    public void start() {

    }

    private class CheckNewVersionCallback implements LoaderManager.LoaderCallbacks<Integer> {

        private void checkNewVersion() {
            mLoaderManager.initLoader(CHECK_NEW_VERSION_QUERY, null, this);
        }

        @Override
        public Loader<Integer> onCreateLoader(int id, Bundle args) {
            return mCheckNewUpdatesLoader;
        }

        @Override
        public void onLoadFinished(Loader<Integer> loader, Integer data) {
            mLoaderManager.destroyLoader(loader.getId());
            if (data != null) {
                try {
                    PackageInfo packageInfo = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0);
                    int installedVersionCode = packageInfo.versionCode;
                    int serverVersionCode = data;

                    if (installedVersionCode == 0) {
                        throw new Exception("Błędna wersja na serwerze, skontakuj się z administratorem");
                    } else if (serverVersionCode > installedVersionCode) {
                        Log.d(TAG, "New version available.\n Yours: " + installedVersionCode + "\n" + "Server: " + serverVersionCode);
                        showNewVersionDialog();
                    } else {
                        Log.d(TAG, "You have the newest version");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onLoaderReset(Loader<Integer> loader) {
        }
    }

    private class DownloadNewVersionCallback implements LoaderManager.LoaderCallbacks<Void> {

        private void downloadNewVersion() {
            mLoaderManager.initLoader(DOWNLOAD_NEW_VERSION_QUERY, null, this);
        }

        @Override
        public Loader<Void> onCreateLoader(int id, Bundle args) {
            return mDownloadNewVersionLoader;
        }

        @Override
        public void onLoadFinished(Loader<Void> loader, Void data) {
            mLoaderManager.destroyLoader(loader.getId());

        }

        @Override
        public void onLoaderReset(Loader<Void> loader) {

        }
    }

}
