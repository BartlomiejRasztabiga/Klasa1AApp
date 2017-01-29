package pl.rasztabiga.klasa1a.countdownsAct;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.rasztabiga.klasa1a.R;

public class CountdownsFragment extends Fragment {


    //TODO This class is maybe not needed
    @BindView(R.id.countdownTitle)
    TextView countdownTitle;
    @BindView(R.id.countdownTime)
    TextView countdownTime;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    public CountdownsFragment() {

    }

    public static CountdownsFragment newInstance() {
        return new CountdownsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.countdown_fragment, container, false);
        ButterKnife.bind(this, root);

        return root;
    }
}
