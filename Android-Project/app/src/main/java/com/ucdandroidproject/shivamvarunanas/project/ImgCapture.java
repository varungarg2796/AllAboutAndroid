package com.ucdandroidproject.shivamvarunanas.project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/*
Created by Varun Garg
This class handles the functionality of clicking an image
 */
public class ImgCapture extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_capture);

        Button btnCamera = (Button) findViewById(R.id.buttonCam);
        imageView = (ImageView) findViewById(R.id.imageView2);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
                getPermission();
            }
        });
    }

    @Override
    //Lets the user know the result of an activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data!=null && data.getExtras().get("data")!=null){
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);}
    }

    //Checks if the permission is there and if not, gets it
    public void getPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ) {
            String PERMISSIONS_REQUIRED[] = new String[]{
                    Manifest.permission.CAMERA,
            };
            ActivityCompat.requestPermissions(this, PERMISSIONS_REQUIRED, 1);
        }
    }
}
