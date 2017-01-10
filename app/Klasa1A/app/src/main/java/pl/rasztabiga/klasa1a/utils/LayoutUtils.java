package pl.rasztabiga.klasa1a.utils;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import pl.rasztabiga.klasa1a.ExamsCalendarActivity;
import pl.rasztabiga.klasa1a.MainActivity;
import pl.rasztabiga.klasa1a.R;

public class LayoutUtils {

    private static final String TAG = LayoutUtils.class.getName();

    public static Drawer getNavigationDrawer(final Activity actualClass, int selectedItem, Toolbar toolbar) {
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Ekran główny").withIcon(ResourcesCompat.getDrawable(actualClass.getResources(), R.drawable.calendar_icon, null)).withTag(MainActivity.class);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Kalendarz sprawdzianów").withIcon(ResourcesCompat.getDrawable(actualClass.getResources(), R.drawable.home_icon, null)).withTag(ExamsCalendarActivity.class);

        return new DrawerBuilder().withActivity(actualClass)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggle(true)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        item2
                )
                .withSelectedItem(selectedItem)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Log.d(TAG, drawerItem.getTag().toString());
                        Log.d(TAG, getClass().getEnclosingClass().toString());
                        if (((Class) drawerItem.getTag()).equals(actualClass.getClass()))
                            return false;
                        Intent intent = new Intent(actualClass, (Class) drawerItem.getTag());
                        actualClass.startActivity(intent);
                        actualClass.finish();
                        return false;
                    }
                })
                .build();
    }
}
