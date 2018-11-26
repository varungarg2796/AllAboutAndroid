package com.ucdandroidproject.shivamvarunanas.project;

import java.io.Serializable;

/*CREATED BY SHIVAM RATHORE
THIS CLASS IS USED TO REPRESENT A TRACK*/
class Track implements Serializable {
    String trackName;
    String latitude;
    String longitude;
    String videoUrl;
    int trackId;

    public Track(String trackName, String latitude, String longitude, String videoUrl, int trackId) {
        this.trackName = trackName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.videoUrl = videoUrl;
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
