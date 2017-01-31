package pl.rasztabiga.klasa1a.splashAct;


import pl.rasztabiga.klasa1a.BasePresenter;
import pl.rasztabiga.klasa1a.BaseView;

public interface EnterApiKeyContract {

    interface View extends BaseView<Presenter> {

        void onSubmitApiKey();

        void showApiKeyError();

        void acceptApiKey();

        void setProgressIndicator(boolean active);
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void checkIsApiKeyValid(String apiKeyToValidate);


    }
}
