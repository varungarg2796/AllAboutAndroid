package com.ucdandroidproject.shivamvarunanas.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_Screen2_Screen_Photos extends TopActivityScreen2 implements GetDataFromFlickr_JSON.DataAvailableToWork, ClickListener_RecyclerItem.OnClickListener_RecyclerItem {
    private static final String TAG = "MainActivity";
    private RecyclerViewAdapter_FLICKR recyclerViewAdapterFLICKR;
    Track track = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_photos);
        enableBackToHomeToolbar(true);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new ClickListener_RecyclerItem(this, recyclerView, this));
        recyclerViewAdapterFLICKR = new RecyclerViewAdapter_FLICKR(this, new ArrayList<Photo>());
        recyclerView.setAdapter(recyclerViewAdapterFLICKR);
        setTitle("Track");
//        GetRawData getRawData = new GetRawData(this);
//        getRawData.execute("https://api.flickr.com/services/feeds/photos_public.gne?tags=android,nougat,sdk&tagmode=any&format=json&nojsoncallback=1");

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: ");
        Intent intent = new Intent(this, PhotoDetailActivityScreen2.class);
        intent.putExtra(PHOTO_TRANSFER, recyclerViewAdapterFLICKR.getPhoto(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Log.d(TAG, "onItemLongClick: ");
        Intent intent = new Intent(this, PhotoDetailActivityScreen2.class);
        intent.putExtra(PHOTO_TRANSFER, recyclerViewAdapterFLICKR.getPhoto(position));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        Intent intent1 = getIntent();
        if(track == null) {
            track = (Track) intent1.getSerializableExtra("TRACK");
        }
        Log.d(TAG, "onResume starts with intent : "+track.getTrackName());
        super.onResume();
        GetDataFromFlickr_JSON getDataFromFlickrJSON = new GetDataFromFlickr_JSON(this, "https://api.flickr.com/services/feeds/photos_public.gne", "en-us", false);
//        getDataFromFlickrJSON.runInSameThread("android, nougat");
        getDataFromFlickrJSON.execute(track.getTrackName());
        setTitle(track.getTrackName());
        Log.d(TAG, "onResume ends");
    }





    @Override
    public void onDataAvailable(List<Photo> data, DownloadStatus status) {

        if(status == DownloadStatus.SUCCESS) {
            recyclerViewAdapterFLICKR.loadNewData(data);
        } else {
            // download or processing failed
            Log.e(TAG, "onDataAvailable failed with status " + status);
        }
    }














}
