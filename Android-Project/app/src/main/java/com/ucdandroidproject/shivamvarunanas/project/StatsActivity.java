package com.ucdandroidproject.shivamvarunanas.project;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
Created by Varun Garg
Displays the statistics according to the month
 */
public class StatsActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_stats);
        String TAG = "StatsActivity";
        Button buttonCelebrate = (Button) findViewById(R.id.buttonCelebrate);

        mDatabaseHelper = new DatabaseHelper(this);


        int total_distance = mDatabaseHelper.getSumValue();
        Log.d(TAG, "onClick " + total_distance);


        int total_time = mDatabaseHelper.getTimeSum();
        Log.d(TAG, "onClick " + total_time);

        showSummary(total_distance,total_time);

        buttonCelebrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (getApplicationContext(),ImgCapture.class);
                startActivity(intent);
            }
        });

    }

    public void showSummary(int dist, int time){


        TextView distanceText = (TextView) findViewById(R.id.distanceView);
        TextView timeText = (TextView) findViewById(R.id.timeView);



        if(dist < 1000){
            distanceText.setText(""+ dist + " m");
        } else{
            dist = dist/1000;
            distanceText.setText(""+ dist + " kms");
        }

        if (time < 60) {
            timeText.setText("" + time + " s");
        } else {
            time = time/60;
            timeText.setText("" + time + " mins");
        }

    }
}
