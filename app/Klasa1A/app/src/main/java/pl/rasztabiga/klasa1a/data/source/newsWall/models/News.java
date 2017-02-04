package pl.rasztabiga.klasa1a.data.source.newsWall.models;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class News {

    @NonNull
    private int mId;

    @NonNull
    private String mTitle;

    @Nullable
    private String mDescription;

    public News(){

    }

    public News(@NonNull String title, @NonNull int id) {
        this(id, title, null);

    }

    public News(@NonNull int id, @NonNull String title, String description) {
        this.mId = id;
        this.mTitle = title;
        this.mDescription = description;
    }

    @NonNull
    public int getId() {
        return mId;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }
}
