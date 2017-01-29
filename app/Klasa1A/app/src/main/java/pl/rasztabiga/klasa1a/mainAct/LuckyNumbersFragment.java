package pl.rasztabiga.klasa1a.mainAct;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.rasztabiga.klasa1a.R;
import pl.rasztabiga.klasa1a.data.source.luckyNumbers.models.LuckyNumbers;

public class LuckyNumbersFragment extends Fragment implements LuckyNumbersContract.View {

    @BindView(R.id.monday_ln)
    TextView mMondayLuckyNumber;
    @BindView(R.id.tuesday_ln)
    TextView mTuesdayLuckyNumber;
    @BindView(R.id.wednesday_ln)
    TextView mWednesdayLuckyNumber;
    @BindView(R.id.thursday_ln)
    TextView mThursdayLuckyNumber;
    @BindView(R.id.friday_ln)
    TextView mFridayLuckyNumber;
    private LuckyNumbersContract.Presenter mPresenter;

    public LuckyNumbersFragment() {
    }

    public static LuckyNumbersFragment newInstance() {
        return new LuckyNumbersFragment();
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
    public void setPresenter(LuckyNumbersContract.Presenter presenter) {
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
        View root = inflater.inflate(R.layout.luckunumbers_fragment, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void showLuckyNumbers(LuckyNumbers luckyNumbers) {

        //When monday is set to 0, don't show lucky numbers
        if (luckyNumbers != null && luckyNumbers.getNumbersList().get(0) != 0) {
            List<Integer> luckyNumbersList = luckyNumbers.getNumbersList();
            mMondayLuckyNumber.setText(String.valueOf(luckyNumbersList.get(0)));
            mTuesdayLuckyNumber.setText(String.valueOf(luckyNumbersList.get(1)));
            mWednesdayLuckyNumber.setText(String.valueOf(luckyNumbersList.get(2)));
            mThursdayLuckyNumber.setText(String.valueOf(luckyNumbersList.get(3)));
            mFridayLuckyNumber.setText(String.valueOf(luckyNumbersList.get(4)));

            mMondayLuckyNumber.setVisibility(View.VISIBLE);
            mTuesdayLuckyNumber.setVisibility(View.VISIBLE);
            mWednesdayLuckyNumber.setVisibility(View.VISIBLE);
            mThursdayLuckyNumber.setVisibility(View.VISIBLE);
            mFridayLuckyNumber.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showLoadingLuckyNumbersError() {

    }
}
