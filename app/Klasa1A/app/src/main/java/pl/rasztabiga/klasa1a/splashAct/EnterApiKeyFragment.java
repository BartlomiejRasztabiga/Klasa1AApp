package pl.rasztabiga.klasa1a.splashAct;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.rasztabiga.klasa1a.R;

public class EnterApiKeyFragment extends Fragment implements EnterApiKeyContract.View {

    @BindView(R.id.submitApiKeyButton)
    Button submitButton;
    @BindView(R.id.apiKeyEditText)
    EditText apiKeyEditText;
    @BindView(R.id.loggingInProgressBar)
    ProgressBar progressIndicator;
    @BindView(R.id.apiKeyErrorTextView)
    TextView errorTextView;

    @OnClick(R.id.submitApiKeyButton)
    public void submitApiKey() {
        onSubmitApiKey();
    }

    private EnterApiKeyContract.Presenter mPresenter;

    public static EnterApiKeyFragment newInstance() {

        return new EnterApiKeyFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.enter_api_key_fragment, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void setPresenter(EnterApiKeyContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }

    @Override
    public void onSubmitApiKey() {
        if (!apiKeyEditText.getText().toString().equals("") && apiKeyEditText.getText() != null) {
            mPresenter.checkIsApiKeyValid(apiKeyEditText.getText().toString());
        }
    }

    @Override
    public void showApiKeyError() {
        errorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void acceptApiKey() {
        Intent intent = new Intent(getActivity(), SplashActivity.class);
        startActivity(intent);
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if (active) {
            progressIndicator.setVisibility(View.VISIBLE);
        } else {
            progressIndicator.setVisibility(View.INVISIBLE);
        }
    }
}
