package com.ucdandroidproject.shivamvarunanas.project;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.ucdandroidproject.shivamvarunanas.project.R.color.accent;
import static com.ucdandroidproject.shivamvarunanas.project.R.color.primary_light;

/*CREATED BY ANAS
* This class handles all the the sending the of the data so depending on what the user clicks on
* here the user is going to preform the run
* here we are calculating all the relvent information which we will pass on to the graph view
* for example how long it took to preform every km
*/

public class MainActivity_Screen1 extends AppCompatActivity {

    static final int REQUEST_CODE_ACCESS_LOCATION = 1;
    private static final String TAG = "MainActivity_Screen1";
    private static boolean ACCSES_LOCATION_GRANTED = false;
    public boolean start = false;
    DatabaseHelper mDatabaseHelper; //added by me
    boolean running;

    Double s;
    Drawable d;
    Double currSpeed;
    String shownDistance;
    private ArrayList<DetailsActivity> userActMonitoring;
    private LocationManager lm;
    private Location loc = null;
    private Double distTra = 0.0;
    private TextView currentSpeed, averageSpeed, distanceTra;
    private Button startBtn, stopBtn, resetBtn;
    private Chronometer cm;
    private int trackId;

    void checkPermission() {
        int hasAccessLocation = PermissionChecker.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        Log.d(TAG, "checkPermission: = " + hasAccessLocation);
        if (hasAccessLocation == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "checkPermission: Granted");
            ACCSES_LOCATION_GRANTED = true;
        } else {
            Log.d(TAG, "checkPermission: Requesting ");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_ACCESS_LOCATION);

        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: Starts");
        switch (requestCode) {
            case REQUEST_CODE_ACCESS_LOCATION: {
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: true");
                    ACCSES_LOCATION_GRANTED = true;
                } else {
                    Toast.makeText(this, "Cannot Function without access to Location. Please Grant Permission", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onRequestPermissionsResult: Exit");
                    finish();

                }
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        s = new Double(0);
        currSpeed = new Double(0);
        setContentView(R.layout.activity_main_screen1);
        final Intent intent = getIntent();
        trackId = intent.getIntExtra("TRACK_ID", 0);
        Log.d(TAG, "onCreate: -------" + trackId);
        userActMonitoring = new ArrayList<DetailsActivity>();
        mDatabaseHelper = new DatabaseHelper(this);


        currentSpeed = (TextView) findViewById(R.id.userSpeed);
        averageSpeed = (TextView) findViewById(R.id.userAvgSpeed);
        distanceTra = (TextView) findViewById(R.id.userDistance);

        cm = (Chronometer) findViewById(R.id.chronometerTime);
        d = cm.getBackground();
        startBtn = (Button) findViewById(R.id.btn_start);
        stopBtn = (Button) findViewById(R.id.btn_stop);
        resetBtn = (Button) findViewById(R.id.btn_reset);
        //getPermission();

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        final LocationListener ll = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {

                    DetailsActivity user = new DetailsActivity();
                    user.longitude(location.getLongitude());
                    user.latitude(location.getLatitude());

                    // Speed calculation
                    Double userSpeed = (double) location.getSpeed();
                    user.setSpeed(userSpeed * 3.6);
                    s = userSpeed * 3.6;
                    currSpeed = 0.0;

                    if (userActMonitoring.isEmpty()) {
                        currSpeed = s;
                    }
                    // if there is more than one time we get the avaerge
                    else {
                        currSpeed = getAvgSpeed();
                    }

                    // Distance calculation
                    if (loc == null) {
                        loc = location;
                    }
                    double currLocation = loc.distanceTo(location);
                    distTra = distTra + currLocation;
                    user.setDistTra(distTra);


                    shownDistance = "";
                    if (distTra < 1000) {
                        // make to a whole number
                        shownDistance = (int) Math.round(distTra) + " meters";
                    } else {
                        // make it two decimal points
                        shownDistance = String.format("%.2f", distTra / 1000) + " km";
                    }


                    // time in seconds which is uesed in graphs
                    long elapsedMillisecs = SystemClock.elapsedRealtime() - cm.getBase();
                    long timePassed = elapsedMillisecs / 1000;
                    user.setTime(timePassed);

                    currentSpeed.setText("Current speed : " + (int) Math.round(s) + " km/hour");
                    averageSpeed.setText("Average speed: " + (int) Math.round(currSpeed) + " km/hour");
                    distanceTra.setText("Distance Traveled: " + shownDistance);


                    loc = location;
                    userActMonitoring.add(user);
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
            }
        };
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTracker(ll);
                reset();
                cm.setBackground(d);
            //    cm.setBackgroundColor(Color.parseColor("#3498DB"));
                cm.setTextColor(Color.parseColor("#F4F4F4"));
            }
        });
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (start != true) {
                    cm.setBase(SystemClock.elapsedRealtime());
                    cm.start();
                    enableTracker(ll);
                    start = true;
//
//                    cm.setBackgroundColor(Color.parseColor("#96ED89"));
//                    cm.setBackgroundColor(getResources().getColor(R.color.primary_dark));
//                    cm.setTextColor(Color.parseColor("#393939"));
                    running = true;
                } else
                    Toast.makeText(MainActivity_Screen1.this, "Tracking has Commence", Toast.LENGTH_LONG).show();

            }
        });

        //functionality to add to the database has been created by Varun Garg
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double distanceInMetres = 0;
                Intent userSummary = new Intent(MainActivity_Screen1.this, SummaryActivity.class);
                endTracker(ll);
                Bundle userData = new Bundle();
                cm.setBackgroundColor(getResources().getColor(primary_light));
                running = false;
                if (!userActMonitoring.isEmpty()) {
                    userData.putInt("avgSpeed", (int) Math.round(getAvgSpeed()));
                    //get the last elemnet in the array to get total distance
                    Double dist = userActMonitoring.get(userActMonitoring.size() - 1).getDistTra();
                    String distance = "";
                    if (dist < 1000) {
                        distance = (int) Math.round(dist) + " meters";
                    } else {
                        distance = String.format("%.2f", dist / 1000) + " km";
                    }

                    userData.putString("distTaveled", distance);

                    long totalTime = userActMonitoring.get(userActMonitoring.size() - 1).getTime();
                    String userTime = "" + totalTime;
                    userSummary.putExtra("TIME_TO_BEAT",userTime);
                    userData.putString("userTotalTime", userTime);
                    //code to add to DB
                    String user_distance = "" + dist;

                    if (dist != 0) {
                        AddData(user_distance, userTime);
                    } else {
                        toastMessage("You must run!");
                    }

                    //
                    //array list to store the times and pass on to the next activity
                    ArrayList<Integer> graphData = graphData();
                    userData.putIntegerArrayList("graphArray", graphData);
                    distanceInMetres = dist;
                }


                reset();

                userSummary.putExtra("TRACK_ID",trackId);
                userSummary.putExtra("DISTANCE_IN_METRES",distanceInMetres);
                userSummary.putExtras(userData);
                startActivity(userSummary);
           //     cm.setBackgroundColor(Color.parseColor("#3498DB"));
           //     cm.setTextColor(Color.parseColor("#F4F4F4"));
            }
        });


    }



    public void enableTracker(LocationListener ll) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, ll);
        }


    }


    public void endTracker(LocationListener ll) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.removeUpdates(ll);
    }

    //get the user average speed
    public double getAvgSpeed() {
        double result = 0;
        for (int i = 0; i < userActMonitoring.size(); i++) {
            result = result + userActMonitoring.get(i).getSpeed();
        }
        return result / userActMonitoring.size();
    }

    //reset and clear the data
    public void reset() {
        loc = null;
        start = false;
        currentSpeed.setText("Current Speed");
        averageSpeed.setText("Average Speed");
        cm.stop();
        cm.setText("00:00");
        userActMonitoring.removeAll(userActMonitoring);
        distTra = 0.0;
        distanceTra.setText("Distance Traveled");
        loc = null;
    }

    //save the time in secs for each kilometer completed
    public ArrayList<Integer> graphData() {
        ArrayList<Integer> times = new ArrayList<>();
        double oneKMcheck = 1000;
        for (int i = 0; i < userActMonitoring.size(); i++) {
            if (userActMonitoring.get(i).getDistTra() >= oneKMcheck) {
                oneKMcheck = oneKMcheck + 1000;
                times.add((int) Math.round(userActMonitoring.get(i).getTime()));
            }
        }
        return times;
    }

    //Method that adds tp local DB created by Varun Garg
    public void AddData(String newEntry_1, String newEntry_2) {
        boolean insertData = mDatabaseHelper.addData(newEntry_1, newEntry_2);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putDouble("CURRENT_1", s);
        savedInstanceState.putString("DISTANCE", shownDistance);
        savedInstanceState.putString("CHRONO", (String) cm.getText());
        savedInstanceState.putBoolean("RUNNING", running);
        savedInstanceState.putDouble("SPEED", currSpeed);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        s = savedInstanceState.getDouble("CURRENT_1");
        shownDistance = savedInstanceState.getString("DISTANCE");
        currSpeed = savedInstanceState.getDouble("SPEED");
        currentSpeed.setText("Current speed : " + (int) Math.round(s) + " km/hour");
        distanceTra.setText("Distance Traveled: " + shownDistance);
        averageSpeed.setText("Current speed : " + (int) Math.round(currSpeed) + " km/hour");
        int timeInMilSeconds = Integer.parseInt(savedInstanceState.getString("CHRONO").split(":")[1]) * 1000;
        if (savedInstanceState.getBoolean("RUNNING")) {
            cm.setBase(SystemClock.elapsedRealtime() - timeInMilSeconds);
            cm.setBackgroundColor(Color.parseColor("#96ED89"));
            cm.setTextColor(Color.parseColor("#393939"));
            cm.start();
            running = true;
        }
    }

    public void onResume() {
        super.onResume();
    }
}
