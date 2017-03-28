package com.shikhar.eroslab;

import android.net.http.HttpResponseCache;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    DownloadItemsAdapter mAdapter;
    List<String> mListOfLinks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //add sample URLs
        addSampleData();

        //cache for pause/resume functionality
        File httpCacheDir = new File(this.getCacheDir(), "http");
        long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
        try {
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new DownloadItemsAdapter(mListOfLinks, this, httpCacheDir);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    void addSampleData(){
        //only last link was working in that google doc

        mListOfLinks.add("https://goo.gl/7nzu65");
        mListOfLinks.add("https://goo.gl/7nzu65");
        mListOfLinks.add("https://goo.gl/7nzu65");
        mListOfLinks.add("https://goo.gl/7nzu65");
        mListOfLinks.add("https://goo.gl/7nzu65");
        mListOfLinks.add("https://goo.gl/7nzu65");
        mListOfLinks.add("https://goo.gl/7nzu65");
        mListOfLinks.add("https://goo.gl/7nzu65");
    }
}
