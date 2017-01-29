package pl.rasztabiga.klasa1a.mainAct;

import pl.rasztabiga.klasa1a.BasePresenter;
import pl.rasztabiga.klasa1a.BaseView;
import pl.rasztabiga.klasa1a.data.LuckyNumbers;

public interface LuckyNumbersContract {

    interface View extends BaseView<Presenter> {

        //void setLoadingIndicator(boolean active);

        void showLuckyNumbers(LuckyNumbers luckyNumbers);

        void showLoadingLuckyNumbersError();

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadLuckyNumbers(boolean forceUpdate);
    }

}
