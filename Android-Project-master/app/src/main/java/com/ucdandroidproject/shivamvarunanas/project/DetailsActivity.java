package com.ucdandroidproject.shivamvarunanas.project;


public class DetailsActivity {
    double speed, latitude, longitude , distanceTracked;
    long seconds;

    void setSpeed(Double s){
        speed = s;
    }
    Double getSpeed(){
        return speed;
    }
    void latitude(Double lat){
        latitude = lat;
    }
    void longitude(Double lon){longitude  = lon;}
    void setDistTra(Double dt){
        distanceTracked = dt;
    }
    Double getDistTra(){
        return distanceTracked;
    }
    void setTime(long st){
        seconds = st;
    }
    long getTime(){
        return seconds;
    }
}
