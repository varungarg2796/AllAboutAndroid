package com.ucdandroidproject.shivamvarunanas.project;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public class SummaryActivity extends Activity {
    TextView speedTextView,distanceTextView,timeTextView;
    GraphView graphView;
    ArrayList<Integer> userData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        speedTextView = (TextView)findViewById(R.id.userSpeed);
        distanceTextView = (TextView)findViewById(R.id.userDistance);
        timeTextView = (TextView)findViewById(R.id.userTime);

        graphView = (GraphView)findViewById(R.id.graphLayOut);
        Bundle data = getIntent().getExtras();
        int speed = data.getInt("avgSpeed");
        String totalDistance = data.getString("distTaveled");
        String totalTime = data.getString("userTotalTime");
        if(totalDistance== null){
            totalDistance="0";
        }
        if(totalTime== null){
            totalTime="0";
        }
        userData = data.getIntegerArrayList("graphArray");


        speedTextView.setText("Your average speed : " + speed);
        distanceTextView.setText("Total distance traveled : " + totalDistance);
        timeTextView.setText("Your total time is: " + totalTime + " seconds");
        ArrayList<Integer> userData = new ArrayList<Integer>();
        userData = data.getIntegerArrayList("graphArray");

        if (userData == null || userData.isEmpty()){
            Toast.makeText(SummaryActivity.this, "Not enough data was recorded to display", Toast.LENGTH_LONG).show();
        }
        else {
            ArrayList<Float> temp = new ArrayList<Float>();
            //send the data to GraphView and display on graph
            for (int i = 0; i < userData.size(); i++) {
                float x = (float) userData.get(i);
                temp.add(x);
            }

            graphView.setTemp(temp);
        }


    }
}