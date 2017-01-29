package pl.rasztabiga.klasa1a.splashAct;


import pl.rasztabiga.klasa1a.BasePresenter;
import pl.rasztabiga.klasa1a.BaseView;

public interface EnterApiKeyContract {

    interface View extends BaseView<Presenter> {

        void showApiKeyError();

        void showLoadingApiKeyError();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void checkIsApiKeyValid();
    }
}
