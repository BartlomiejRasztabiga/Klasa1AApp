package pl.rasztabiga.klasa1a.calendarAct;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.etiennelawlor.imagegallery.library.activities.FullScreenImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.activities.ImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.adapters.FullScreenImageGalleryAdapter;
import com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pl.rasztabiga.klasa1a.data.source.examPhotos.ExamsPhotosLoader;
import pl.rasztabiga.klasa1a.data.source.exams.models.Exam;

import static com.google.common.base.Preconditions.checkNotNull;

public class ExamsPhotosPresenter implements ExamsPhotosContract.Presenter, LoaderManager.LoaderCallbacks<List<String>>, ImageGalleryAdapter.ImageThumbnailLoader, FullScreenImageGalleryAdapter.FullScreenImageLoader {

    private final static int GET_EXAMS_PHOTOS_QUERY = 7;

    private final static String IMAGES_SERVER_URL = "http://94.177.229.18/images/";

    private final ExamsPhotosLoader mLoader;

    private final LoaderManager mLoaderManager;

    private final Activity mExamsCalendarFragment;

    private Context mContext;

    public ExamsPhotosPresenter(Exam examToShowPhotos, @NonNull LoaderManager loaderManager, @NonNull Context context, @NonNull Activity examsCalendarFragment) {
        mLoader = new ExamsPhotosLoader(context);
        mLoader.setExamIdToFetchImages(examToShowPhotos);
        mLoaderManager = checkNotNull(loaderManager);
        mContext = checkNotNull(context);
        mExamsCalendarFragment = checkNotNull(examsCalendarFragment);
    }


    public void getImagesUrls() {
        mLoaderManager.initLoader(GET_EXAMS_PHOTOS_QUERY, null, this);
    }


    @Override
    public void start() {
        ImageGalleryActivity.setImageThumbnailLoader(this);
        FullScreenImageGalleryActivity.setFullScreenImageLoader(this);
    }

    @Override
    public Loader<List<String>> onCreateLoader(int id, Bundle args) {
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<String>> loader, List<String> data) {
        mLoaderManager.destroyLoader(loader.getId());
        if (data != null && !data.isEmpty()) {
            createGallery(data);
        } else {
            showNoImagesToast();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<String>> loader) {

    }

    private void createGallery(List<String> imagesUrlList) {
        ArrayList<String> imagesRealUrl = new ArrayList<>();
        for (String s : imagesUrlList) {
            imagesRealUrl.add(IMAGES_SERVER_URL + s);
        }

        Intent intent = new Intent(mExamsCalendarFragment, ImageGalleryActivity.class);


        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ImageGalleryActivity.KEY_IMAGES, imagesRealUrl);
        bundle.putString(ImageGalleryActivity.KEY_TITLE, "Zdjęcia sprawdzianu");
        intent.putExtras(bundle);

        mContext.startActivity(intent);
    }

    private void showNoImagesToast() {
        Toast.makeText(mExamsCalendarFragment, "Nie ma zdjęć tego sprawdzianu!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadImageThumbnail(ImageView iv, String imageUrl, int dimension) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(iv.getContext())
                    .load(imageUrl)
                    .resize(dimension, dimension)
                    .centerCrop()
                    .into(iv);
        } else {
            iv.setImageDrawable(null);
        }
    }

    @Override
    public void loadFullScreenImage(final ImageView iv, String imageUrl, int width, LinearLayout bglinearLayout) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(iv.getContext())
                    .load(imageUrl)
                    //.resize(width, 0)
                    .into(iv, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                        }
                    });
        } else {
            iv.setImageDrawable(null);
        }
    }
}
