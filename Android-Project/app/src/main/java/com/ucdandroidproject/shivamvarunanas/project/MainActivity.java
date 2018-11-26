package com.ucdandroidproject.shivamvarunanas.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView MsgText;
    DatabaseHelper_Firebase fDatabashehelper;
//    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//    private DatabaseReference mRootReference = firebaseDatabase.getReference("runs");
//    private DatabaseReference mChildReference = mRootReference.child("Time");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button statBut = (Button)findViewById(R.id.btn_stat);
        Button button = (Button)findViewById(R.id.btn_run);

        Button btn_tracks = (Button)findViewById(R.id.btn_tracks);

//        fDatabashehelper = new DatabaseHelper_Firebase();
//        fDatabashehelper.addToFirebase();
//        fDatabashehelper.getData("1");

        statBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (getApplicationContext(),ImgCapture.class);
                startActivity(intent);

            }
        });
        btn_tracks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity_Screen2.class));
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (getApplicationContext(),MainActivity_Screen1.class);
                startActivity(intent);
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        mChildReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                String message = dataSnapshot.getValue(String.class);
//                MsgText.setText(message);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

//    public void writeToDatabase(){
//        String TAG = "MainActivity";
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference();
//        //myRef = database.getReference("/test/data/message1");
//        myRef.setValue("Hello, World!");
//
//        Log.e(TAG, "onClick: "+myRef.toString());
//        //Log.e(TAG, "onClick: "+lol.toString());
//    }
}


