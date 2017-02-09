package pl.rasztabiga.klasa1a.newsWallAct;


import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.rasztabiga.klasa1a.R;
import pl.rasztabiga.klasa1a.data.source.newsWall.models.News;
import pl.rasztabiga.klasa1a.data.source.newsWall.models.NewsAdapter;

public class NewsWallFragment extends Fragment implements NewsWallContract.View {

    @BindView(R.id.recyclerview_newswall)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_indicator_newswall)
    ProgressBar mProgressBar;
    @BindView(R.id.news_error_message_tv)
    TextView mErrorMessage;
    private NewsWallContract.Presenter mPresenter;
    private NewsAdapter mNewsAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (getView() == null) {
            return;
        }

        if (active) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void showNews(List<News> newsList) {
        if(newsList != null && !newsList.isEmpty()){
            mNewsAdapter.setNewsData(newsList);
            mErrorMessage.setVisibility(View.INVISIBLE);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.newswall_fragment, container, false);
        ButterKnife.bind(this, root);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mNewsAdapter = new NewsAdapter();
        mRecyclerView.setAdapter(mNewsAdapter);
        //setHasOptionMenu(true);
        return root;
    }

    @Override
    public void showLoadingNewsError() {
        setLoadingIndicator(false);
        mErrorMessage.setVisibility(View.VISIBLE);


    }


    @Override
    public void setPresenter(NewsWallContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }

    }

    public static NewsWallFragment newInstance() {
        return new NewsWallFragment();
    }
}
