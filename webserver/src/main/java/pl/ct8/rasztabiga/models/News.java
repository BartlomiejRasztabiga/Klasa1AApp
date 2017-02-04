package pl.ct8.rasztabiga.models;


public final class News {

    private int mId;

    private String mTitle;

    private String mDescription;

    public News() {

    }

    public News(int id, String  title) {
        this(id, title, null);

    }

    public News(int id, String title, String description) {
        this.mId = id;
        this.mTitle = title;
        this.mDescription = description;
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }
}
