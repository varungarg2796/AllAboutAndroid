package com.ucdandroidproject.shivamvarunanas.project;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/*Created by ANAS
* this class is taking in the data and displaying it in real time
* meaning as you are running it is getting information and displaying it for the user to view
*/
public class SummaryActivity extends Activity {
    TextView speedTextView, distanceTextView, timeTextView;
    GraphView graphView;
    ArrayList<Integer> userData;

    String enteredName = null;

    String TAG = "SummaryActivity";

    //Method to display the toast for the current record holder
    private void chnageMethodNameAcordingly(String name, String time, ArrayList<String> record) {
        Log.d(TAG, "onClick: -!-! Record : " + record);

        if (record != null && record.size() > 0) {
            name = record.get(1);
            time = record.get(2);
        }
        Toast.makeText(this, "Current Track Record is held by " + name + " and the best time is : " + time + " seconds", Toast.LENGTH_LONG).show();
        Log.d(TAG, "Name is" + time);
    }


    private void showError() {
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        speedTextView = (TextView) findViewById(R.id.userSpeed);
        distanceTextView = (TextView) findViewById(R.id.userDistance);
        timeTextView = (TextView) findViewById(R.id.userTime);
        graphView = (GraphView) findViewById(R.id.graphLayOut);

        checkForNewRecord();


    }
    //this method checks to see if a new record has been already created
    void checkForNewRecord() {

        Bundle data = getIntent().getExtras();
        int speed = data.getInt("avgSpeed");
        String totalDistance = data.getString("distTaveled");
        String totalTime = data.getString("userTotalTime");


        //     final ArrayList<String> work = new ArrayList<String>();

        if (totalDistance == null) {
            totalDistance = "0 ";
        }
        if (totalTime == null) {
            totalTime = "0";
        }
        userData = data.getIntegerArrayList("graphArray");
        final double dist = data.getDouble("DISTANCE_IN_METRES",0);

        final int total_time = Integer.parseInt(totalTime);
        Log.i(TAG, "checkForNewRecord: :::::::;"+dist);
        final Intent intent = getIntent();
        final String name = "Emulator";
        final String time = "Emulated Time";
        final int trackId = intent.getIntExtra("TRACK_ID", 0);




        /*
        Below if block and the associated methods have been created by Varun Garg to check if the person has beaten the time of the previous person or not
         */
        if (dist>1000 && trackId != 0) {

            String time_to_beat = intent.getStringExtra("TIME_TO_BEAT");
            Log.d(TAG, "Time to beat" + time_to_beat);
            DatabaseHelper_Firebase.getData(trackId + "", new OneMethodInterface() {
                @Override
                public void doSomething(Object object) {
                    if (object instanceof Boolean) {
                        showError();
                    } else if (object instanceof ArrayList) {
                        //Intent intent = new Intent (MainActivity_Screen1.this, SummaryActivity.class);

                        ArrayList<String> record = (ArrayList<String>) object;
                        chnageMethodNameAcordingly(name, time, record);
                        //intent.putExtra("record", record);
//                        work.add(record.get(1));
//                        work.add(record.get(2));
                        Log.d(TAG, "onClick: -!-! Record : " + record);
                        int timeToBeat = Integer.parseInt(record.get(2));


                        if (total_time < timeToBeat) {

                            final AlertDialog.Builder alert = new AlertDialog.Builder(SummaryActivity.this);

                            alert.setTitle("Congratulations! You have set a new track record!");
                            alert.setMessage("Please enter your name!");
                            final EditText input = new EditText(SummaryActivity.this);
                            alert.setView(input);
                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    String track_id = String.valueOf(trackId);
                                    String total_time_f = String.valueOf(total_time);
                                    String name = input.getText().toString();


                                    // Do something with value!
                                    DatabaseHelper_Firebase.addToFirebase(track_id, total_time_f, name);


                                    // Log.d(TAG,"Tag" + total_time_f + track_id + name);

                                }
                            });

                            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    // Canceled.
                                }
                            });

                            if (alert != null) {
                                //then show your dialog..else not
                                alert.show();
                            }


                        }

                    }
                }
            });
        }


        speedTextView.setText("Your average speed : " + speed);
        distanceTextView.setText("Total distance traveled : " + totalDistance);
        timeTextView.setText("Your total time is: " + totalTime + " seconds");
        ArrayList<Integer> userData = new ArrayList<Integer>();
        userData = data.getIntegerArrayList("graphArray");

        //checks if a the data is either not empty and not null
        if (userData == null || userData.isEmpty()) {
            Toast.makeText(SummaryActivity.this, "Not enough data was recorded to display", Toast.LENGTH_LONG).show();
        } else {
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