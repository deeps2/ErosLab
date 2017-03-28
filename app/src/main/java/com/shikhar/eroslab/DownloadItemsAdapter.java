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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.VISIBLE;
import static com.shikhar.eroslab.R.id.url;

public class DownloadItemsAdapter extends RecyclerView.Adapter<DownloadItemsAdapter.DownloadItemsViewHolder> {

    private static List<String> mListOfUrls;
    private static Context mContext;
    public static File outputFileCache;

    public DownloadItemsAdapter(List<String> list, Context context, File cache) {
        this.mListOfUrls = list;
        this.mContext = context;
        outputFileCache = cache;
    }

    public static class DownloadItemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(url)
        TextView mUrl;
        @BindView(R.id.state)
        TextView mState;
        @BindView(R.id.progress)
        ProgressBar mProgress;
        @BindView(R.id.not_started)
        Button mNotStarted_Download;
        @BindView(R.id.waiting)
        Button mWaiting_Stop;
        @BindView(R.id.downloading)
        LinearLayout mDownloading;
        @BindView(R.id.pause)
        Button mDownloading_Pause;
        @BindView(R.id.stop1)
        Button mDownloading_Stop;
        @BindView(R.id.paused)
        LinearLayout mPaused;
        @BindView(R.id.resume)
        Button mPaused_Resume;
        @BindView(R.id.stop2)
        Button mPaused_Stop;
        @BindView(R.id.downloaded)
        Button mDonwloaded_Delete;


        public DownloadItemsViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            mNotStarted_Download.setOnClickListener(this);
            mWaiting_Stop.setOnClickListener(this);
            mDownloading_Pause.setOnClickListener(this);
            mDownloading_Stop.setOnClickListener(this);
            mPaused_Resume.setOnClickListener(this);
            mPaused_Stop.setOnClickListener(this);
            mDonwloaded_Delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (view.getId() == mNotStarted_Download.getId()) {

                mState.setText("Downloading...");
                view.setVisibility(View.INVISIBLE);
                mWaiting_Stop.setVisibility(VISIBLE);

                startDownloading(mListOfUrls.get(getAdapterPosition()));

            } else if (view.getId() == mWaiting_Stop.getId()) {

               // Toast.makeText(view.getContext(), "2" + getAdapterPosition(), Toast.LENGTH_SHORT).show();

            } else if (view.getId() == mDownloading_Pause.getId()) {

                //Toast.makeText(view.getContext(), "3" + getAdapterPosition(), Toast.LENGTH_SHORT).show();

            } else if (view.getId() == mDownloading_Stop.getId()) {

               // Toast.makeText(view.getContext(), "4" + getAdapterPosition(), Toast.LENGTH_SHORT).show();

            } else if (view.getId() == mPaused_Resume.getId()) {

               // Toast.makeText(view.getContext(), "5" + getAdapterPosition(), Toast.LENGTH_SHORT).show();

            } else if (view.getId() == mPaused_Stop.getId()) {

               // Toast.makeText(view.getContext(), "6" + getAdapterPosition(), Toast.LENGTH_SHORT).show();

            } else if (view.getId() == mDonwloaded_Delete.getId()) {

                //Toast.makeText(view.getContext(), "7" + getAdapterPosition(), Toast.LENGTH_SHORT).show();

            } else {
                //
            }
        }
    }

    @Override
    public DownloadItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new DownloadItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DownloadItemsViewHolder holder, int position) {

        holder.mUrl.setText(mListOfUrls.get(position));  //show URL path in TextView
        holder.mState.setText("Not Started..");  //show Not Started as default status

    }

    @Override
    public int getItemCount() {
        return mListOfUrls.size();
    }


    //for start/pause/resume functionality: http://stackoverflow.com/questions/6237079/resume-http-file-download-in-java
    static void startDownloading(String urlString) {

        URL url = null;

        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (outputFileCache.exists()) {
            connection.setAllowUserInteraction(true);
            connection.setRequestProperty("Range", "bytes=" + outputFileCache.length() + "-");
        }

        connection.setConnectTimeout(14000);
        connection.setReadTimeout(20000);


        final HttpURLConnection connection1 = connection;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connection1.connect();

                    String connectionField = connection1.getHeaderField("content-range");
                    Long downloadedSize = 0L;

                    if (connectionField != null) {
                        String[] connectionRanges = connectionField.substring("bytes=".length()).split("-");
                        downloadedSize = Long.valueOf(connectionRanges[0]);
                    }

                    if (connectionField == null && outputFileCache.exists()) {
                        outputFileCache.delete();
                       /* outputFileCache = new File(mContext.getCacheDir(), "http");
                        long httpCacheSize = 10 * 1024 * 1024;

                        try {
                            HttpResponseCache.install(outputFileCache, httpCacheSize);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/

                    }

                    Long fileLength = connection1.getContentLength() + downloadedSize;
                    BufferedInputStream input = new BufferedInputStream(connection1.getInputStream());

                   //TODO: getting exception here: java.io.FileNotFoundException: /data/user/0/com.shikhar.eroslab/cache/http: open failed: EISDIR (Is a directory)
                    RandomAccessFile output = new RandomAccessFile(outputFileCache, "rw");
                    output.seek(downloadedSize);

                    byte data[] = new byte[1024];
                    int count = 0;
                    int __progress = 0;

                    while ((count = input.read(data, 0, 1024)) != -1
                            && __progress != 100) {
                        downloadedSize += count;
                        output.write(data, 0, count);
                        __progress = (int) ((downloadedSize * 100) / fileLength);
                    }

                    output.close();
                    input.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
