package com.ucdandroidproject.shivamvarunanas.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/*Created by SHIVAM RATHORE.
This class represents the screen which appears when we click on the photo tab*/
public class MainActivity_Screen2_Screen_Photos extends TopActivityScreen2 implements GetDataFromFlickr_JSON.DataAvailableToWork, ClickListener_RecyclerItem.OnClickListener_RecyclerItem {

    Track track = null;
    private RecyclerViewAdapter_FLICKR recyclerViewAdapterFLICKR;

    @Override
    //Initialize the Adapters in the current context, and passing a new ArrayList of Flickr photos type
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_photos);
        enableBackToHomeToolbar(true);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new ClickListener_RecyclerItem(this, recyclerView, this));
        recyclerViewAdapterFLICKR = new RecyclerViewAdapter_FLICKR(this, new ArrayList<FlickrPhoto>());
        recyclerView.setAdapter(recyclerViewAdapterFLICKR);
        setTitle("Track");


    }

    @Override
    //Overriden method from ClickListener_ReycclerItem to display the enlarged photo
    public void onItemClick(View view, int position) {

        Intent intent = new Intent(this, PhotoDetailActivityScreen2.class);
        intent.putExtra(PHOTO_TRANSFER, recyclerViewAdapterFLICKR.getFlickrPhoto(position));
        startActivity(intent);
    }

    @Override
    //Overriden method from ClickListener_ReycclerItem to display the enlarged photo
    public void onItemLongClick(View view, int position) {

        Intent intent = new Intent(this, PhotoDetailActivityScreen2.class);
        intent.putExtra(PHOTO_TRANSFER, recyclerViewAdapterFLICKR.getFlickrPhoto(position));
        startActivity(intent);
    }

    @Override
    //Get the track which is selected on previous screen. Also, from here, we send a get request to the Flickr passing the selected track name's as the parameter
    protected void onResume() {
        Intent intent1 = getIntent();
        if (track == null) {
            track = (Track) intent1.getSerializableExtra("TRACK");
        }

        super.onResume();
        GetDataFromFlickr_JSON getDataFromFlickrJSON = new GetDataFromFlickr_JSON(this, "https://api.flickr.com/services/feeds/photos_public.gne", "en-us", false);

        getDataFromFlickrJSON.execute(track.getTrackName());
        setTitle(track.getTrackName());

    }


    @Override
    //Implement the callback when new Data is Available, sent the created list of photo objects to the Recycler View Adapter to display
    public void onDataAvailable(List<FlickrPhoto> data, DownloadStatus status) {

        if (status == DownloadStatus.SUCCESS) {
            recyclerViewAdapterFLICKR.loadNewData(data);
        } else {
            // download or processing failed

        }
    }


}
