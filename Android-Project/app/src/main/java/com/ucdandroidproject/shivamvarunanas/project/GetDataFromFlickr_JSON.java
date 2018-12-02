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
    private final DataAvailableToWork dataAvailableCallback;
    private List<FlickrPhoto> flickrPhotoList = null;
    private String URL;
    private String language;
    private boolean match;
    private boolean runningOnSameThread = false;

    //Constructor
    public GetDataFromFlickr_JSON(DataAvailableToWork callBack, String baseURL, String language, boolean matchAll) {
        Log.d(TAG, "GetDataFromFlickr_JSON called");
        URL = baseURL;
        dataAvailableCallback = callBack;
        this.language = language;
        match = matchAll;
    }

    @Override
    //When the List of photo objects has been created
    protected void onPostExecute(List<FlickrPhoto> flickrPhotos) {

        if (dataAvailableCallback != null) {
            dataAvailableCallback.onDataAvailable(this.flickrPhotoList, DownloadStatus.SUCCESS);
        }
    }



    @Override
    ////Call the Get Raw Data class to retrieve the JSON Async Task
    protected List<FlickrPhoto> doInBackground(String... params) {

        String UriToExecute = createUri(params[0], language, match);

        GetRawData getRawData = new GetRawData(this);
        getRawData.run_CurrentThread(UriToExecute);
        return flickrPhotoList;
    }

    //Create Uri which is Flickr recognizable from the criterion provided
    private String createUri(String searchCriteria, String lang, boolean matchAll) {


        return Uri.parse(URL).buildUpon()
                .appendQueryParameter("tags", searchCriteria)
                .appendQueryParameter("tagmode", matchAll ? "ALL" : "ANY")
                .appendQueryParameter("lang", lang)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .build().toString();
    }

    @Override
    //Method which creates the array list of photos from retrieved JSON
    public void retrievalCompleted(String data, DownloadStatus status) {


        if (status == DownloadStatus.SUCCESS) {
            flickrPhotoList = new ArrayList<>();

            try {
                JSONObject dataObject = new JSONObject(data);
                JSONArray dataArray = dataObject.getJSONArray("items");

                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject jsonPhoto = dataArray.getJSONObject(i);
                    String title = jsonPhoto.getString("title");
                    String author = jsonPhoto.getString("author");
                    String authorId = jsonPhoto.getString("author_id");
                    String tags = jsonPhoto.getString("tags");

                    JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                    String photoUrl = jsonMedia.getString("m");

                    String link = photoUrl.replaceFirst("_m.", "_b.");

                    FlickrPhoto flickrPhotoObject = new FlickrPhoto(title, author, authorId, link, tags, photoUrl);
                    flickrPhotoList.add(flickrPhotoObject);


                }
            } catch (JSONException e) {
                e.printStackTrace();

                status = DownloadStatus.INVALID;
            }
        }

        if (runningOnSameThread && dataAvailableCallback != null) {
            // now inform the caller that processing is done - possibly returning null if there
            // was an error
            dataAvailableCallback.onDataAvailable(flickrPhotoList, status);
        }


    }

    //Interface to implement callback
    interface DataAvailableToWork {
        void onDataAvailable(List<FlickrPhoto> data, DownloadStatus status);
    }
}
