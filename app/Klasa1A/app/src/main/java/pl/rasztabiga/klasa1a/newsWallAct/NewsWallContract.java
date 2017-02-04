package pl.rasztabiga.klasa1a.newsWallAct;


import java.util.List;

import pl.rasztabiga.klasa1a.BasePresenter;
import pl.rasztabiga.klasa1a.BaseView;
import pl.rasztabiga.klasa1a.data.source.newsWall.models.News;


public interface NewsWallContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showNews(List<News> newsList);

        void showLoadingNewsError();


    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadNews(boolean forceUpdate);


    }
}
