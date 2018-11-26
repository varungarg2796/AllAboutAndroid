package com.ucdandroidproject.shivamvarunanas.project;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatabaseHelper_Firebase {


    private static final String TAG = "DatabaseHelper_Firebase";
    public static void addToFirebase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference track1 = database.getReference("0");
        DatabaseReference track2 = database.getReference("1");
        track2.child("time").setValue("100");
        track2.child("distance").setValue("2000");
        track2.child("name").setValue("Varun");

    }


    public static void getData(String ID, final OneMethodInterface oneMethodInterface) {

        Log.d(TAG, "getData: -!-!  ID : " + ID);
        final ArrayList<String> prop = new ArrayList<>();
        prop.clear();
        FirebaseDatabase.getInstance().getReference(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String a = snapshot.getValue(String.class);
                    prop.add(a);
                    Log.i("value: ", a);
                }

                oneMethodInterface.doSomething(prop);
                Log.d(TAG, "onDataChange: -!-! : Size: " + prop.size());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                oneMethodInterface.doSomething(false);
            }


        });

        Log.d(TAG, "getData: Here : -!-! size : " + prop.size());
    }

//    public String getTime(int ID){
//        String time = "";
//
//        return time;
//    }
}
