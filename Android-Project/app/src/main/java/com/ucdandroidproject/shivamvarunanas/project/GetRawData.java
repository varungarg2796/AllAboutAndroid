package com.ucdandroidproject.shivamvarunanas.project;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



/*Created by Shivam Rathore
Class which gets raw data from any Uri. This class retrieves the data from flickr API which is passed to Get Data From Flickr_JSON.*/

enum DownloadStatus {WAITING, IN_PROGRESS, NOT_READY, INVALID, SUCCESS}

class GetRawData extends AsyncTask<String, Void, String> {


    private final DataRetrievalComplete callBack_RawData;
    private DownloadStatus status;

    public GetRawData(DataRetrievalComplete callback) {
        this.status = DownloadStatus.WAITING;
        callBack_RawData = callback;
    }

    //This is called from Get Data from Flickr Json (In the same thread) and from here we further process in a background thread
    void run_CurrentThread(String s) {


        if (callBack_RawData != null) {
            callBack_RawData.retrievalCompleted(doInBackground(s), status);
        }


    }

    @Override
    //What to do when the background processing is complete
    protected void onPostExecute(String s) {

        if (callBack_RawData != null) {
            callBack_RawData.retrievalCompleted(s, status);
        }

    }

    @Override
    //This task is executed in a background thread. After completion onPostExecute is called
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        if (strings == null) {
            status = DownloadStatus.NOT_READY;
            return null;
        }

        try {
            status = DownloadStatus.IN_PROGRESS;
            URL url = new URL(strings[0]);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int response = connection.getResponseCode();


            StringBuilder result = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                result.append(line).append("\n");
            }

            status = DownloadStatus.SUCCESS;
            return result.toString();


        } catch (MalformedURLException e) {

        } catch (IOException e) {

        } catch (SecurityException e) {

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {

                }
            }
        }

        status = DownloadStatus.INVALID;
        return null;
    }

    //Interface to implement Callback
    interface DataRetrievalComplete {
        void retrievalCompleted(String data, DownloadStatus status);
    }

}