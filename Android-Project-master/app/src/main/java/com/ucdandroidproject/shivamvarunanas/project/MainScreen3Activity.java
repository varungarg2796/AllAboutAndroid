package com.ucdandroidproject.shivamvarunanas.project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainScreen3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen3);


        try{
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Runs",MODE_PRIVATE,null);

            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS runs (ID INTEGER PRIMARY KEY AUTOINCREMENT, distance INT(2), time INT(2))");

            //myDatabase.execSQL("INSERT INTO runs (distance,time) VALUES (100,200)");
            //myDatabase.execSQL("INSERT INTO runs (distance,time) VALUES (9,8)");
            //myDatabase.execSQL("DELETE FROM runs where time = 200");

            Cursor c = myDatabase.rawQuery("SELECT*FROM runs", null);
            int distanceIndex = c.getColumnIndex("distance");
            int timeIndex = c.getColumnIndex("time");
            int idIndex = c.getColumnIndex("ID");

            c.moveToFirst();

            while(c!=null){
                Log.i("distance",c.getString(distanceIndex));
                Log.i("time",c.getString(timeIndex));
                Log.i("ID",c.getString(idIndex));
                c.moveToNext();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
