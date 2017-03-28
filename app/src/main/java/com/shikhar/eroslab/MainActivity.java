package com.shikhar.eroslab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

        addSampleData();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new DownloadItemsAdapter(mListOfLinks, this);

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
