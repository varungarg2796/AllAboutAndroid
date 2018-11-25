package com.ucdandroidproject.shivamvarunanas.project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView MsgText;
//    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//    private DatabaseReference mRootReference = firebaseDatabase.getReference("runs");
//    private DatabaseReference mChildReference = mRootReference.child("Time");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button statBut = (Button)findViewById(R.id.btn_stat);
        Button button = (Button)findViewById(R.id.btn_run);


        writeToDatabase();

        statBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (getApplicationContext(),StatsActivity.class);
                startActivity(intent);

            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (getApplicationContext(),MainActivity_screen1.class);
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


