package com.ucdandroidproject.shivamvarunanas.project;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus {WAITING, IN_PROGRESS, NOT_READY, INVALID, SUCCESS}



class GetRawData extends AsyncTask<String, Void, String> {
    private static final String TAG = "GetRawData";

    private DownloadStatus status;
    private final DownloadComplete callBack_RawData;

    interface DownloadComplete {
        void DownloadCompleted(String data, DownloadStatus status);
    }

    public GetRawData(DownloadComplete callback) {
        this.status = DownloadStatus.WAITING;
        callBack_RawData = callback;
    }

    void run_CurrentThread(String s) {
        Log.d(TAG, "run_CurrentThread starts");

//        onPostExecute(doInBackground(s));
        if(callBack_RawData != null) {
//            String result = doInBackground(s);
//            callBack_RawData.DownloadCompleted(result, status);
            callBack_RawData.DownloadCompleted(doInBackground(s), status);
        }

        Log.d(TAG, "run_CurrentThread ends");
    }

    @Override
    protected void onPostExecute(String s) {
//        Log.d(TAG, "onPostExecute: parameter = " + s);
        if(callBack_RawData != null) {
            callBack_RawData.DownloadCompleted(s, status);
        }
        Log.d(TAG, "onPostExecute: ends");
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        if(strings == null) {
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

            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                result.append(line).append("\n");
            }

            status = DownloadStatus.SUCCESS;
            return result.toString();


        } catch(MalformedURLException e) {
            Log.e(TAG, "doInBackground: Invalid URL " + e.getMessage() );
        } catch(IOException e) {
            Log.e(TAG, "doInBackground: IO Exception reading data: " + e.getMessage() );
        } catch(SecurityException e) {
            Log.e(TAG, "doInBackground: Security Exception. Needs permission? " + e.getMessage());
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
            if(reader != null) {
                try {
                    reader.close();
                } catch(IOException e) {

                }
            }
        }

        status = DownloadStatus.INVALID;
        return null;
    }

}