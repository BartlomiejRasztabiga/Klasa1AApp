package pl.rasztabiga.klasa1a.calendarAct;

import pl.rasztabiga.klasa1a.BasePresenter;

public interface ExamsPhotosContract {

    interface Presenter extends BasePresenter {

        void getImagesUrls();

        //void createGallery(List<String> imagesUrlList);
    }
}
