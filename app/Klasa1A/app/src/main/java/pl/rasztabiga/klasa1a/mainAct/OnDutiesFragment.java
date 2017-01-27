package pl.rasztabiga.klasa1a.mainAct;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.rasztabiga.klasa1a.R;
import pl.rasztabiga.klasa1a.data.OnDuties;

public class OnDutiesFragment extends Fragment implements OnDutiesContract.View {

    private OnDutiesContract.Presenter mPresenter;

    private LinearLayout mOnDutiesView;

    private TextView mFirstOnDuty;

    private TextView mSecondOnDuty;

    public OnDutiesFragment() {

    }

    public static OnDutiesFragment newInstance() {
        return new OnDutiesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(OnDutiesContract.Presenter presenter) {
        if(presenter != null) {
            mPresenter = presenter;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.onduties_fragment, container, false);

        mFirstOnDuty = (TextView) root.findViewById(R.id.onDuty1);
        mSecondOnDuty = (TextView) root.findViewById(R.id.onDuty2);
        Log.d("OnDutiesFragment", "Jestem tu");

        //setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showOnDuties(OnDuties onDuties) {

    }

    @Override
    public void showLoadingOnDutiesError() {

    }
}
