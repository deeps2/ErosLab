package com.shikhar.eroslab;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DownloadItemsAdapter  extends RecyclerView.Adapter<DownloadItemsAdapter.DownloadItemsViewHolder> {

    private List<String> mListOfUrls;
    private Context mContext;


    public static class DownloadItemsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.url) TextView mUrl;
        @BindView(R.id.state)TextView mState;
        @BindView(R.id.progress)ProgressBar mProgress;
        @BindView(R.id.not_started)Button mNotStarted_Download;
        @BindView(R.id.waiting)Button mWaiting_Stop;
        @BindView(R.id.downloading)LinearLayout mDownloading;
        @BindView(R.id.pause)Button mDownloading_Pause;
        @BindView(R.id.stop1)Button mDownloading_Stop;
        @BindView(R.id.paused)LinearLayout mPaused;
        @BindView(R.id.resume)Button mPaused_Resume;
        @BindView(R.id.stop2)Button mPaused_Stop;
        @BindView(R.id.downloaded)Button mDonwloaded_Delete;

        public DownloadItemsViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

    }

    public DownloadItemsAdapter(List<String> list, Context context) {
        this.mListOfUrls = list;
        this.mContext = context;
    }

    @Override
    public DownloadItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new DownloadItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DownloadItemsViewHolder holder, int position) {

        holder.mUrl.setText(mListOfUrls.get(position));

    }

    @Override
    public int getItemCount() {
        return mListOfUrls.size();
    }
}
