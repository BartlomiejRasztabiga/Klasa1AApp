package pl.rasztabiga.klasa1a.data.source.newsWall.models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.rasztabiga.klasa1a.R;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    //TODO Add listener to show detail of news

    private static final String TAG = NewsAdapter.class.getSimpleName();

    public ArrayList<News> getmNewsList() {
        return mNewsList;
    }

    private ArrayList<News> mNewsList;

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.news_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        News news = mNewsList.get(position);
        holder.mNewsTitleTextView.setText(news.getTitle());
        holder.mNewsDescTextView.setText(news.getDescription());
    }

    @Override
    public int getItemCount() {
        if (mNewsList == null) return 0;
        return mNewsList.size();
    }
    public void setNewsData(List<News> newsList){
        mNewsList = new ArrayList<>(newsList);

        notifyDataSetChanged();
    }


    class NewsViewHolder extends RecyclerView.ViewHolder {

        public final LinearLayout mNewsData;
        public final TextView mNewsDescTextView;
        public final TextView mNewsTitleTextView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            mNewsData = (LinearLayout) itemView.findViewById(R.id.news_data);
            mNewsTitleTextView = (TextView) itemView.findViewById(R.id.tv_news_title);
            mNewsDescTextView = (TextView) itemView.findViewById(R.id.tv_news_desc);
        }
    }
}