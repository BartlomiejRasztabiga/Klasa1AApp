package pl.rasztabiga.klasa1a.utils;


public class FirebaseUtils {
    private static FirebaseDatabase mDatabase;

    private FirebaseUtils() {
    }

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }

}
