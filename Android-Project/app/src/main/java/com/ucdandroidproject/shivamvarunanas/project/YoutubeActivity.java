package com.ucdandroidproject.shivamvarunanas.project;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/*CREATED BY SHIVAM RATHORE
THIS CLASS IS USED TO PLAY THE VIDEO CORRESPONDING TO EACH TRACK*/

//Source code referred from Google's Developer Page for creating YouTube Activity
public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    static final String GOOGLE_API_KEY = "AIzaSyCyQYUL038m3VrRg-76RMQUIDny6BhIzAc";

    static  String videoID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_youtube, null);

        setContentView(constraintLayout);

        YouTubePlayerView youTubePlayerView = new YouTubePlayerView(this);
        youTubePlayerView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        constraintLayout.addView(youTubePlayerView);
        youTubePlayerView.initialize(GOOGLE_API_KEY, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Track track = (Track) getIntent().getSerializableExtra("TRACK");
        videoID = track.getVideoUrl();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {

        if (!restored) {
            youTubePlayer.cueVideo(videoID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        final int REQUEST_CODE = 1;
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, REQUEST_CODE).show();
        } else {

            Toast.makeText(this, "Error Initialising", Toast.LENGTH_LONG).show();
        }
    }
}
