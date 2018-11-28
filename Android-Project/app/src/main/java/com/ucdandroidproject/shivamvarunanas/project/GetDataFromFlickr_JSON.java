package com.ucdandroidproject.shivamvarunanas.project;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*Created by Shivam Rathore
This class parses the retrieved JSON to create a list of photo objects*/

class GetDataFromFlickr_JSON extends AsyncTask<String, Void, List<FlickrPhoto>> implements GetRawData.DataRetrievalComplete {
    private static final String TAG = "GetDataFromFlickr_JSON";

    private List<FlickrPhoto> mFlickrPhotoList = null;
    private String URL;
    private String lang;
    private boolean match;

    private final DataAvailableToWork dataAvailableCallback;
    private boolean runningOnSameThread = false;

    //Interface to implement callback
    interface DataAvailableToWork {
        void onDataAvailable(List<FlickrPhoto> data, DownloadStatus status);
    }

    //Constructor
    public GetDataFromFlickr_JSON(DataAvailableToWork callBack, String baseURL, String language, boolean matchAll) {
        Log.d(TAG, "GetDataFromFlickr_JSON called");
        URL = baseURL;
        dataAvailableCallback = callBack;
        lang = language;
        match = matchAll;
    }

    //Call the Get Raw Data class to retrieve the JSON in the same thread
//    void runInSameThread(String searchCriteria) {
//        Log.d(TAG, "runInSameThread starts");
//        runningOnSameThread = true;
//        String destinationUri = createUri(searchCriteria, lang, match);
//
//        GetRawData getRawData = new GetRawData(this);
//        getRawData.execute(destinationUri);
//        Log.d(TAG, "runInSameThread ends");
//    }

    @Override
    //When the List of photo objects has been created
    protected void onPostExecute(List<FlickrPhoto> flickrPhotos) {
        Log.d(TAG, "onPostExecute starts");

        if(dataAvailableCallback != null) {
            dataAvailableCallback.onDataAvailable(mFlickrPhotoList, DownloadStatus.SUCCESS);
        }
        Log.d(TAG, "onPostExecute ends");
    }

    @Override
    ////Call the Get Raw Data class to retrieve the JSON Async Task
    protected List<FlickrPhoto> doInBackground(String... params) {
        Log.d(TAG, "doInBackground starts");
        String destinationUri = createUri(params[0], lang, match);

        GetRawData getRawData = new GetRawData(this);
        getRawData.run_CurrentThread(destinationUri);
        Log.d(TAG, "doInBackground ends");
        return mFlickrPhotoList;
    }

    //Create Uri which is Flickr recognizable from the criterion
    private String createUri(String searchCriteria, String lang, boolean matchAll) {
        Log.d(TAG, "createUri starts");

        return Uri.parse(URL).buildUpon()
                .appendQueryParameter("tags", searchCriteria)
                .appendQueryParameter("tagmode", matchAll ? "ALL" : "ANY")
                .appendQueryParameter("lang", lang)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .build().toString();
    }

    @Override
    //Method whic created the array list of photos from retrieved JSON
    public void retrievalCompleted(String data, DownloadStatus status) {
        Log.d(TAG, "retrievalCompleted starts. Status = " + status);

        if(status == DownloadStatus.SUCCESS) {
            mFlickrPhotoList = new ArrayList<>();

            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("items");

                for(int i=0; i<itemsArray.length(); i++) {
                    JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                    String title = jsonPhoto.getString("title");
                    String author = jsonPhoto.getString("author");
                    String authorId = jsonPhoto.getString("author_id");
                    String tags = jsonPhoto.getString("tags");

                    JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                    String photoUrl = jsonMedia.getString("m");

                    String link = photoUrl.replaceFirst("_m.", "_b.");

                    FlickrPhoto flickrPhotoObject = new FlickrPhoto(title, author, authorId, link, tags, photoUrl);
                    mFlickrPhotoList.add(flickrPhotoObject);

                    Log.d(TAG, "retrievalCompleted " + flickrPhotoObject.toString());
                }
            } catch(JSONException jsone) {
                jsone.printStackTrace();
                Log.e(TAG, "retrievalCompleted: Error processing Json data " + jsone.getMessage());
                status = DownloadStatus.INVALID;
            }
        }

        if(runningOnSameThread && dataAvailableCallback != null) {
            // now inform the caller that processing is done - possibly returning null if there
            // was an error
            dataAvailableCallback.onDataAvailable(mFlickrPhotoList, status);
        }

        Log.d(TAG, "retrievalCompleted ends");
    }
}
