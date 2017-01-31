package pl.rasztabiga.klasa1a.updater;


import pl.rasztabiga.klasa1a.BasePresenter;

public interface UpdaterContract {

    interface Presenter extends BasePresenter {

        void checkNewVersion();

        void downloadNewVersion();

        void showNewVersionDialog();
    }
}
