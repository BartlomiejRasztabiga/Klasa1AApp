package pl.rasztabiga.klasa1a;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etiennelawlor.imagegallery.library.activities.FullScreenImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.activities.ImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.adapters.FullScreenImageGalleryAdapter;
import com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter;
import com.etiennelawlor.imagegallery.library.enums.PaletteColorType;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.rasztabiga.klasa1a.models.Exam;
import pl.rasztabiga.klasa1a.models.ExamAdapter;
import pl.rasztabiga.klasa1a.utils.LayoutUtils;
import pl.rasztabiga.klasa1a.utils.NetworkUtilities;

public class ExamsCalendarActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, ExamAdapter.ExamClickListener, ImageGalleryAdapter.ImageThumbnailLoader, FullScreenImageGalleryAdapter.FullScreenImageLoader {

    private static final String TAG = ExamsCalendarActivity.class.getName();
    private static final int GET_EXAMS_LOADER = 33;
    private final Calendar calendar = Calendar.getInstance();
    private final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("LLLL yyyy", Locale.getDefault());

    @BindView(R.id.compactcalendar_view)
    CompactCalendarView compactCalendarView;
    @BindView(R.id.date_tv)
    TextView date_tv;
    @BindView(R.id.recyclerview_exams)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ExamAdapter mExamAdapter;

    private SharedPreferences preferences;
    private String apiKey;

    private PaletteColorType paletteColorType;


    //TODO remove calendar.set()


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests_calendar);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ImageGalleryActivity.setImageThumbnailLoader(this);
        FullScreenImageGalleryActivity.setFullScreenImageLoader(this);

        LayoutUtils.getNavigationDrawer(ExamsCalendarActivity.this, 2, toolbar);

        paletteColorType = PaletteColorType.VIBRANT;

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        apiKey = preferences.getString(getString(R.string.apiKey_pref_key), "");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mExamAdapter = new ExamAdapter(this);

        mRecyclerView.setAdapter(mExamAdapter);

        getSupportLoaderManager().initLoader(GET_EXAMS_LOADER, null, this);


        //Show date and events for actual day
        /*Date presentDate = new Date();
        date_tv.setText(dateFormat.format(presentDate));
        getSupportActionBar().setTitle(simpleDateFormat.format(presentDate));
        List<Event> events = compactCalendarView.getEvents()*/

        //TODO Use joda time

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                calendar.setTime(dateClicked);

                date_tv.setText(dateFormat.format(dateClicked));

                ArrayList<Exam> examArrayList = new ArrayList<>();
                for (Event e : events) {
                    examArrayList.add((Exam) e.getData());
                }

                mExamAdapter.setExamsData(examArrayList);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                getSupportActionBar().setTitle(simpleDateFormat.format(firstDayOfNewMonth));
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the MainActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case GET_EXAMS_LOADER: {
                return new AsyncTaskLoader<String>(this) {
                    String examsJson;

                    @Override
                    protected void onStartLoading() {
                        if (examsJson != null) {
                            deliverResult(examsJson);
                        } else {
                            forceLoad();
                        }
                    }

                    @Override
                    public String loadInBackground() {
                        try {
                            return NetworkUtilities.getExams(apiKey);
                        } catch (RequestException e) {
                            FirebaseCrash.logcat(Log.ERROR, TAG, "RequestException caught");
                            FirebaseCrash.report(e);
                            Log.d(TAG, e.getMessage());
                            return null;
                        }
                    }

                    @Override
                    public void deliverResult(String data) {
                        examsJson = data;
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
            case GET_EXAMS_LOADER: {
                if (data != null && !data.equals("")) {
                    setEvents(data);
                }

                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    private void setEvents(String JSONString) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<ArrayList<Exam>>() {
        }.getType();
        ArrayList<Exam> examsArrayList = gson.fromJson(JSONString, collectionType);


        ArrayList<Event> eventArrayList = new ArrayList<>();
        for (Exam e : examsArrayList) {
            eventArrayList.add(e.createEvent());
        }

        compactCalendarView.addEvents(eventArrayList);

        Date presentDate = new Date();
        date_tv.setText(dateFormat.format(presentDate));
        getSupportActionBar().setTitle(simpleDateFormat.format(presentDate));

        List<Event> events = compactCalendarView.getEvents(presentDate);

        ArrayList<Exam> examArrayList = new ArrayList<>();
        for (Event e : events) {
            examArrayList.add((Exam) e.getData());
        }

        mExamAdapter.setExamsData(examArrayList);
    }

    @Override
    public void onExamClick(int clickedItemIndex) {
        prepareImagesUris(mExamAdapter.getExamList().get(clickedItemIndex));
    }

    private void showToastNoImagesForExam() {
        Toast.makeText(this, "Nie ma zdjęć tego sprawdzianu!", Toast.LENGTH_SHORT).show();
    }

    private boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    private void prepareImagesUris(Exam examForGettingImages) {
        if (!isStoragePermissionGranted()) return;
        ArrayList<String> associatedImagesListStringArrayList = null;
        try {
            associatedImagesListStringArrayList = new GetAssociatedImagesList().execute(examForGettingImages.getId()).get();
            if (associatedImagesListStringArrayList != null) {
                if (associatedImagesListStringArrayList.isEmpty()) {
                    showToastNoImagesForExam();
                    return;
                }
            } else {
                showToastNoImagesForExam();
                return;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl("gs://klasa1a-app.appspot.com/exams_photos/");

        final Map<String, Uri> associatedImagesUriList = new TreeMap<>();

        if (associatedImagesListStringArrayList != null) {
            final int sizeOfImagesListArrayList = associatedImagesListStringArrayList.size();
            for (final String s : associatedImagesListStringArrayList) {
                StorageReference examStorageRef = storageReference.child(s);
                examStorageRef.getDownloadUrl().addOnSuccessListener(this, new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        associatedImagesUriList.put(s, uri);
                        if (associatedImagesUriList.size() == sizeOfImagesListArrayList)
                            createImageGallery(associatedImagesUriList);
                    }
                });
            }
        }
    }

    private void createImageGallery(Map<String, Uri> downloadUrlsMap) {

        ArrayList<Uri> associatedImagesUriList = new ArrayList<>(downloadUrlsMap.values());
        ArrayList<String> associatedImagesUriAsStringList = new ArrayList<>();
        for (Uri u : associatedImagesUriList) {
            associatedImagesUriAsStringList.add(u.toString());
        }

        Intent intent = new Intent(ExamsCalendarActivity.this, ImageGalleryActivity.class);

        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ImageGalleryActivity.KEY_IMAGES, associatedImagesUriAsStringList);
        bundle.putString(ImageGalleryActivity.KEY_TITLE, "Zdjęcia sprawdzianu");
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    public void loadFullScreenImage(final ImageView iv, String imageUrl, int width, final LinearLayout bglinearLayout) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(iv.getContext())
                    .load(imageUrl)
                    //.resize(width, 0)
                    .into(iv, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
                            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {
                                    applyPalette(palette, bglinearLayout);
                                }
                            });
                        }

                        @Override
                        public void onError() {

                        }
                    });
        } else {
            iv.setImageDrawable(null);
        }
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

    private void applyPalette(Palette palette, ViewGroup viewGroup) {
        int bgColor = getBackgroundColor(palette);
        if (bgColor != -1)
            viewGroup.setBackgroundColor(bgColor);
    }

    private void applyPalette(Palette palette, View view) {
        int bgColor = getBackgroundColor(palette);
        if (bgColor != -1)
            view.setBackgroundColor(bgColor);
    }

    private int getBackgroundColor(Palette palette) {
        int bgColor = -1;

        int vibrantColor = palette.getVibrantColor(0x000000);
        int lightVibrantColor = palette.getLightVibrantColor(0x000000);
        int darkVibrantColor = palette.getDarkVibrantColor(0x000000);

        int mutedColor = palette.getMutedColor(0x000000);
        int lightMutedColor = palette.getLightMutedColor(0x000000);
        int darkMutedColor = palette.getDarkMutedColor(0x000000);

        if (paletteColorType != null) {
            switch (paletteColorType) {
                case VIBRANT:
                    if (vibrantColor != 0) { // primary option
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) { // fallback options
                        bgColor = lightVibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    } else if (mutedColor != 0) {
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) {
                        bgColor = lightMutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    }
                    break;
                case LIGHT_VIBRANT:
                    if (lightVibrantColor != 0) { // primary option
                        bgColor = lightVibrantColor;
                    } else if (vibrantColor != 0) { // fallback options
                        bgColor = vibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    } else if (mutedColor != 0) {
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) {
                        bgColor = lightMutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    }
                    break;
                case DARK_VIBRANT:
                    if (darkVibrantColor != 0) { // primary option
                        bgColor = darkVibrantColor;
                    } else if (vibrantColor != 0) { // fallback options
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) {
                        bgColor = lightVibrantColor;
                    } else if (mutedColor != 0) {
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) {
                        bgColor = lightMutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    }
                    break;
                case MUTED:
                    if (mutedColor != 0) { // primary option
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) { // fallback options
                        bgColor = lightMutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    } else if (vibrantColor != 0) {
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) {
                        bgColor = lightVibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    }
                    break;
                case LIGHT_MUTED:
                    if (lightMutedColor != 0) { // primary option
                        bgColor = lightMutedColor;
                    } else if (mutedColor != 0) { // fallback options
                        bgColor = mutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    } else if (vibrantColor != 0) {
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) {
                        bgColor = lightVibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    }
                    break;
                case DARK_MUTED:
                    if (darkMutedColor != 0) { // primary option
                        bgColor = darkMutedColor;
                    } else if (mutedColor != 0) { // fallback options
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) {
                        bgColor = lightMutedColor;
                    } else if (vibrantColor != 0) {
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) {
                        bgColor = lightVibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    }
                    break;
                default:
                    break;
            }
        }

        return bgColor;
    }

    class GetAssociatedImagesList extends AsyncTask<Integer, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            try {
                Gson gson = new Gson();
                Type collectionType = new TypeToken<ArrayList<String>>() {
                }.getType();
                return gson.fromJson(NetworkUtilities.getAssociatedImagesList(apiKey, params[0]), collectionType);
            } catch (RequestException e) {
                FirebaseCrash.logcat(Log.ERROR, TAG, "RequestException caught");
                FirebaseCrash.report(e);
                e.printStackTrace();
            }

            return null;
        }

    }

}
