package com.ucdandroidproject.shivamvarunanas.project;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

/*CREATED BY SHIVAM RATHORE
This class represents the activity corresponding to the Track Details Page
It gets the current Track as an extra parameter via Intent*/

public class TrackDetails extends AppCompatActivity implements View.OnClickListener, LocationListener {

    private static final String TAG = "TrackDetails";
    private final static int MY_PERMISSIONS_REQUEST_LOCATION = 0;
    Intent intent = null;
    Track track1 = null;
    ImageView photoIcon;
    ImageView videoIcon;
    TextView textView_run;
    TextView textView_stats;
    LocationManager locationManager;
    ImageView mapView;
    GoogleMap googleMap;
    private double latitude = 0;
    private double longitude = 0;

    @Override
    //Store the location in the form of latitude and longitude as soon as there's a change in current location
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        //   Toast.makeText(this, "Current Location: " + location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details);
        mapView = findViewById(R.id.mapView);
        mapView.setOnClickListener(this);
        textView_run = findViewById(R.id.textView_run);
        textView_stats = findViewById(R.id.textView_stats);
        Intent intent1 = getIntent();
        track1 = (Track) intent1.getSerializableExtra("TRACK");
        Log.d(TAG, "onCreate: " + track1.getTrackName());

        photoIcon = findViewById(R.id.imageView_Photo);
        videoIcon = findViewById(R.id.imageView_Video);
        getLocation();
        setTitle(track1.getTrackName());


    }


    @Override
    //Set the Listeners
    protected void onResume() {
        super.onResume();

        photoIcon.setOnClickListener(this);
        videoIcon.setOnClickListener(this);
        textView_stats.setOnClickListener(this);
        textView_run.setOnClickListener(this);
    }


    @Override
    //Depending on which button is clicked, the corresponding activity is called. Current track/trackID is passed as an extra attribute
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = null;
        boolean mapFlag = false;

        Log.d(TAG, "onClick: " + track1.getTrackName());
        switch (id) {
            case R.id.imageView_Photo:
                intent = new Intent(this, MainActivity_Screen2_Screen_Photos.class);
                break;

            case R.id.imageView_Video:
                intent = new Intent(this, YoutubeActivity.class);
                break;

            case R.id.mapView:
                //       String uriString = String.format(Locale.ENGLISH, "geo:%s,%s", track1.latitude, track1.longitude);
                Uri uri = Uri.parse("geo:0,0?q=" + track1.getLatitude() + "," + track1.getLongitude() + "(" + track1.getTrackName() + ")");
                Log.e(TAG, "onClick: :::" + uri);
                intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
                mapFlag = true;
                break;
            // startActivity(intent1);
            case R.id.textView_run:
                Log.d(TAG, "onClick: Track !!!!!_____: " + track1.getLatitude() + " , " + track1.getLongitude());
                Log.d(TAG, "onClick: Current !!!!!_____: " + latitude + " , " + longitude);
                if (track1.getLongitude().substring(0, 5).equals((longitude + "").substring(0, 5)) && track1.getLatitude().substring(0, 5).equals((latitude + "").substring(0, 5))) {
                    intent = new Intent(this, MainActivity_Screen1.class);
                    intent.putExtra("TRACK_ID", track1.trackId);
                }
//                if (new Double(track1.getLatitude()).floatValue() == new Double(latitude).floatValue() && new Double(track1.getLongitude()).floatValue() == new Double(longitude).floatValue()) {
//                    intent = new Intent(this, MainActivity_Screen1.class);
//                    intent.putExtra("TRACK_ID", track1.trackId);
//                }}
                else {
                    Toast.makeText(this, "You are not at the track!", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.textView_stats:
                final String name = "Emulator";
                final String time = "Emulated Time";
                DatabaseHelper_Firebase.getData(track1.trackId + "", new OneMethodInterface() {
                    @Override
                    public void doSomething(Object object) {
                        if (object instanceof Boolean) {
                            showError();
                        } else if (object instanceof ArrayList) {
                            ArrayList<String> record = (ArrayList<String>) object;
                            chnageMethodNameAcordingly(name, time, record);
                        }
                    }
                });
                break;
        }

        if (intent != null && !mapFlag) {
            Log.e(TAG, "onClick:::1" + intent);
            intent.putExtra("TRACK", track1);
            Log.e(TAG, "onClick:::2 " + intent);
            startActivity(intent);
        }
    }

    private void showError() {
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }

    private void chnageMethodNameAcordingly(String name, String time, ArrayList<String> record) {
        Log.d(TAG, "onClick: -!-! Record : " + record);
        if (record != null && record.size() > 0) {
            name = record.get(1);
            time = record.get(2);
        }
        Toast.makeText(this, "Track Record is held by " + name + " and the best time is : " + time, Toast.LENGTH_LONG).show();
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


}
