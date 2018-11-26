package com.ucdandroidproject.shivamvarunanas.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_Screen2 extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity_Screen2";
    List<Track> tracks = new ArrayList<>();
    Button track1;
    Button track2;
    void initializeTracks(){
        Track track = new Track("UCD","53.5", "-6.5", "YvipzNysA7E",1);
        tracks.add(track);
        track = new Track("Central Park", "40", "-73.3","wjn8QcZzmQA",2 );
        tracks.add(track);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__screen2);
        initializeTracks();
        track1 = findViewById(R.id.button_ucd);
        track2 = findViewById(R.id.button_Track2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        track1.setOnClickListener(this);
        track2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent(this, TrackDetails.class);
        switch (id){
            case R.id.ucd_image :

            case R.id.button_ucd:
                intent.putExtra("TRACK", tracks.get(0));
                break;
            case R.id.track2_Image:
            case R.id.button_Track2:
                intent.putExtra("TRACK", tracks.get(1));
                break;
        }
        Log.d(TAG, "onClick: Before starting");
        startActivity(intent);
    }
}
