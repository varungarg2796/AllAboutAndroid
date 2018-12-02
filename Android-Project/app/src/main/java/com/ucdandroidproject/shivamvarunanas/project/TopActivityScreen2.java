package com.ucdandroidproject.shivamvarunanas.project;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/*CREATED BY SHIVAM RATHORE
Define string constants as well as used to enable Action Bar*/

public class TopActivityScreen2 extends AppCompatActivity {
    static final String FLICKR_QUERY = "FLICKR_QUERY";
    static final String PHOTO_TRANSFER = "PHOTO_TRANSFER";
    private static final String TAG = "TopActivityScreen2";


    void enableBackToHomeToolbar    (boolean enableHome) {
        Log.d(TAG, "enableBackToHomeToolbar: ");
        ActionBar actionBar = getSupportActionBar();

        if (actionBar == null) {
            android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                actionBar = getSupportActionBar();
            }
        }

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(enableHome);
        }
    }
}
