package pl.rasztabiga.klasa1a.newsWallAct;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import pl.rasztabiga.klasa1a.Injection;
import pl.rasztabiga.klasa1a.R;
import pl.rasztabiga.klasa1a.data.source.newsWall.NewsLoader;
import pl.rasztabiga.klasa1a.data.source.newsWall.NewsRepository;
import pl.rasztabiga.klasa1a.utils.LayoutUtils;


public class NewsWallActivity extends AppCompatActivity {

    private NewsWallPresenter mNewsWallPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newswall);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LayoutUtils.getNavigationDrawer(NewsWallActivity.this, 4, toolbar);

        NewsWallFragment newsWallFragment = (NewsWallFragment) getSupportFragmentManager().findFragmentById(R.id.newsWallContentFrame);
        if(newsWallFragment == null){
            newsWallFragment = NewsWallFragment.newInstance();
            LayoutUtils.addFragmentToActivity(getSupportFragmentManager(), newsWallFragment, R.id.newsWallContentFrame);
        }
        NewsRepository newsRepository = Injection.provideNewsRepository(getApplicationContext());
        NewsLoader newsLoader = new NewsLoader(getApplicationContext(), newsRepository);

        mNewsWallPresenter = new NewsWallPresenter(
                newsRepository,
                newsWallFragment,
                newsLoader,
                getSupportLoaderManager()

        );

    }
}
