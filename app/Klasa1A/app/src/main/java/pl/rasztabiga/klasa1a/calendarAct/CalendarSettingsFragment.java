package pl.rasztabiga.klasa1a.calendarAct;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import pl.rasztabiga.klasa1a.R;

public class CalendarSettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.pref_calendar);
    }
}
