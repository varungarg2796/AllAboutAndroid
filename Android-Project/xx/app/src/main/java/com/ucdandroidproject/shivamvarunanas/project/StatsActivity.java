package com.ucdandroidproject.shivamvarunanas.project;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        String TAG = "StatsActivity";

        mDatabaseHelper = new DatabaseHelper(this);


        int total_distance = mDatabaseHelper.getSumValue();
        Log.d(TAG, "onClick " + total_distance);

        int total_time = mDatabaseHelper.getTimeSum();
        Log.d(TAG, "onClick " + total_time);


    }
}
