package com.ucdandroidproject.shivamvarunanas.project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*Created by SHIVAM RATHORE
This class is called when the 'Tracks' feature is selected from  main screen.*/
public class MainActivity_Screen2 extends AppCompatActivity implements View.OnClickListener {
    static final int REQUEST_CODE_ACCESS_LOCATION = 1;
    private static final String TAG = "MainActivity_Screen2";
    private static boolean ACCSES_LOCATION_GRANTED = false;
    List<Track> tracks = new ArrayList<>();
    Button track1;
    Button track2;
    ImageView imageView1;
    ImageView imageView2;

    //This method is used to check whether the app has permission or not. If not Permissions are requested from the user
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
    //This method is called when the permission is requested from the user(If not already give)
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: Starts");
        switch (requestCode) {
            case REQUEST_CODE_ACCESS_LOCATION: {
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: true");
                    ACCSES_LOCATION_GRANTED = true;
                } else {
                    Toast.makeText(this, "Cannot Function without access to Location. Please Grant permission", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onRequestPermissionsResult: Exit");
                    finish();

                }
            }
        }
    }

    //Method to hardcore values of the tracks
    void initializeTracks() {
        Track track = new Track("UCD", "53.30499878", "-6.21999912", "YvipzNysA7E", 1);
        tracks.add(track);
        track = new Track("Central Park", "40.7829", "-73.9657831 ", "wjn8QcZzmQA", 2);
        tracks.add(track);
    }

    @Override
    //Initialize variables
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        setContentView(R.layout.activity_main__screen2);
        initializeTracks();
        track1 = findViewById(R.id.button_ucd);
        track2 = findViewById(R.id.button_Track2);
        imageView2 = findViewById(R.id.track2_Image);
        imageView1 = findViewById(R.id.ucd_image);
    }

    @Override
    // Set listeners
    protected void onResume() {
        super.onResume();
        track1.setOnClickListener(this);
        track2.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
    }

    @Override
    // Simply call the Track Details class passing the tapped track as parameter
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent(this, TrackDetails.class);
        switch (id) {
            case R.id.ucd_image:

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
