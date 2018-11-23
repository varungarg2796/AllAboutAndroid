package com.ucdandroidproject.shivamvarunanas.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;



public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "run_stats_fin";
    private static final String COL1 = "ID";
    private static final String COL2 = "distance";
    private static final String COL3 = "time";
    private static final String COL4 = "Date_Time";


    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 +" INT(8),"+ COL3 +" INT(8),"+ COL4 +" DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createTable);
    }

    public boolean addData(String item_1, String item_2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.e(TAG, "addData: ----------"+item_1 + "   "+item_2 );

        Float f1 = new Float(item_1);
        Float f2 = new Float(item_2);
        contentValues.put(COL2, f1.intValue());
        contentValues.put(COL3, f2.intValue());

        Log.d(TAG, "addData: Adding " + item_1 + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);
        Log.d(TAG, "see" + result);
        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}


//public class DatabaseHelper extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_screen3);
//
////double distance , long time
//        try{
//            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Runs",MODE_PRIVATE,null);
//
//            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS runs (ID INTEGER PRIMARY KEY AUTOINCREMENT, distance INT(2), time INT(2))");
//
//            //myDatabase.execSQL("INSERT INTO runs (distance,time) VALUES (100,200)");
//            //myDatabase.execSQL("INSERT INTO runs (distance,time) VALUES (9,8)");
//            //myDatabase.execSQL("DELETE FROM runs where time = 200");
//
//            Cursor c = myDatabase.rawQuery("SELECT*FROM runs", null);
//            int distanceIndex = c.getColumnIndex("distance");
//            int timeIndex = c.getColumnIndex("time");
//            int idIndex = c.getColumnIndex("ID");
//
//            c.moveToFirst();
//
//            while(c!=null){
//                Log.i("distance",c.getString(distanceIndex));
//                Log.i("time",c.getString(timeIndex));
//                Log.i("ID",c.getString(idIndex));
//                c.moveToNext();
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//}
