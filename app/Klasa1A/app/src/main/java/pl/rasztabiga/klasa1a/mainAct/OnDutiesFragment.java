package pl.rasztabiga.klasa1a.mainAct;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.rasztabiga.klasa1a.R;
import pl.rasztabiga.klasa1a.data.source.onDuties.models.OnDuties;
import pl.rasztabiga.klasa1a.data.source.onDuties.models.Student;

public class OnDutiesFragment extends Fragment implements OnDutiesContract.View {

    @BindView(R.id.onDuty1)
    TextView mFirstOnDuty;
    @BindView(R.id.onDuty2)
    TextView mSecondOnDuty;
    @BindView(R.id.progress_indicator)
    ProgressBar mProgressBar;
    private OnDutiesContract.Presenter mPresenter;

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
        if (presenter != null) {
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
        ButterKnife.bind(this, root);

        //setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (getView() == null) {
            return;
        }

        if (active) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showOnDuties(OnDuties onDuties) {
        setLoadingIndicator(false);

        if (onDuties != null) {
            Student[] students = onDuties.getStudentsArray();
            Student first = students[0];
            Student second = students[1];

            mFirstOnDuty.setText(first.getName() + " " + first.getSurname());
            mSecondOnDuty.setText(second.getName() + " " + second.getSurname());
            mFirstOnDuty.setVisibility(View.VISIBLE);
            mSecondOnDuty.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void showLoadingOnDutiesError() {

    }
}
