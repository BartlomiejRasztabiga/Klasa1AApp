package pl.rasztabiga.klasa1a.mainAct;


import pl.rasztabiga.klasa1a.BasePresenter;
import pl.rasztabiga.klasa1a.BaseView;
import pl.rasztabiga.klasa1a.data.source.onDuties.models.OnDuties;

public interface OnDutiesContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showOnDuties(OnDuties onDuties);

        void showLoadingOnDutiesError();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadOnDuties(boolean forceUpdate);
    }
}
