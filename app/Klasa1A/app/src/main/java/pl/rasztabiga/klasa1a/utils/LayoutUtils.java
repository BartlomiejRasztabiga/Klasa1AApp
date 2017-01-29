package pl.rasztabiga.klasa1a.utils;


import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.lang.ref.WeakReference;

import de.cketti.library.changelog.ChangeLog;
import pl.rasztabiga.klasa1a.R;
import pl.rasztabiga.klasa1a.countdownsAct.CountdownsActivity;
import pl.rasztabiga.klasa1a.mainAct.MainActivity;

public class LayoutUtils {

    private static final String TAG = LayoutUtils.class.getName();
    private static final String DOWNLOAD_NEW_VERSION_NAV_DRAWER_TAG = "download_new_version";
    private static final String CHANGELOG_NAV_DRAWER_TAG = "changelog";
    private static WeakReference<Activity> mainActivityRef;

    private LayoutUtils() {}

    public static Drawer getNavigationDrawer(final Activity actualClass, int selectedItem, Toolbar toolbar) {
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Ekran główny").withIcon(ResourcesCompat.getDrawable(actualClass.getResources(), R.drawable.home_icon, null)).withTag(MainActivity.class);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(1).withName("Kalendarz sprawdzianów").withIcon(ResourcesCompat.getDrawable(actualClass.getResources(), R.drawable.calendar_icon, null)).withTag(MainActivity.class);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Odliczanie").withIcon(ResourcesCompat.getDrawable(actualClass.getResources(), R.drawable.clock_icon, null)).withTag(CountdownsActivity.class);
        SecondaryDrawerItem item4 = new SecondaryDrawerItem().withIdentifier(4).withName("Pobierz nową wersję ręcznie").withSelectable(false).withTag(DOWNLOAD_NEW_VERSION_NAV_DRAWER_TAG);
        SecondaryDrawerItem item5 = new SecondaryDrawerItem().withIdentifier(5).withName("Co nowego...").withSelectable(false).withTag(CHANGELOG_NAV_DRAWER_TAG);

        return new DrawerBuilder().withActivity(actualClass)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggle(true)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        item2,
                        new DividerDrawerItem(),
                        item4,
                        item5
                )
                .withSelectedItem(selectedItem)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Object drawerItemTag = drawerItem.getTag();
                        if (drawerItemTag != null) {
                            if (drawerItemTag instanceof Class) {
                                if (drawerItemTag.equals(actualClass.getClass()))
                                    return false;
                                Intent intent = new Intent(actualClass, (Class) drawerItemTag);
                                actualClass.startActivity(intent);
                                actualClass.finish();
                                return false;
                            } else {
                                switch (drawerItemTag.toString()) {
                                    case DOWNLOAD_NEW_VERSION_NAV_DRAWER_TAG: {
                                        //TODO Uzupelnic
                                        break;
                                    }

                                    case CHANGELOG_NAV_DRAWER_TAG: {
                                        ChangeLog changeLog = new ChangeLog(actualClass);
                                        changeLog.getFullLogDialog().show();
                                        break;
                                    }
                                }
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                })
                .build();
    }

    public static void setMainActivityRef(Activity activity) {
        mainActivityRef = new WeakReference<>(activity);
    }

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}

