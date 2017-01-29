package pl.rasztabiga.klasa1a.splashAct;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.rasztabiga.klasa1a.R;

public class EnterApiKeyFragment extends Fragment {

    @BindView(R.id.submitApiKeyButton)
    Button submitButton;
    @BindView(R.id.apiKeyEditText)
    EditText apiKeyEditText;

    //private presenter;

    public EnterApiKeyFragment() {

    }

    public static EnterApiKeyFragment newInstance() {
        return new EnterApiKeyFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Presenter.start();
    }

    //setPresenter


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.enter_api_key_fragment, container, false);
        ButterKnife.bind(this, root);

        return root;
    }
}
