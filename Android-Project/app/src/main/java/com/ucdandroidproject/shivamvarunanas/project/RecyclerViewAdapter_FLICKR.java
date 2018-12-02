package com.ucdandroidproject.shivamvarunanas.project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/*
Created by SHIVAM RATHORE
This is Adapter for Recycler View which returns a new View Holder each time Layout Manager asks for it and returns a single photo
*/

public class RecyclerViewAdapter_FLICKR extends RecyclerView.Adapter<RecyclerViewAdapter_FLICKR.ViewHolderForFlickrImage> {

    private List<FlickrPhoto> flickrPhotoList;
    private Context content;

    public RecyclerViewAdapter_FLICKR(Context content, List<FlickrPhoto> flickrPhotoList) {
        this.flickrPhotoList = flickrPhotoList;
        this.content = content;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderForFlickrImage holder, int position) {
        FlickrPhoto flickrPhotoObject = flickrPhotoList.get(position);
        Picasso.get().load(flickrPhotoObject.getImage()).error(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(holder.smallImage);
        holder.title.setText(flickrPhotoObject.getTitle());


    }

    @NonNull
    @Override

    public ViewHolderForFlickrImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list, parent, false
        );
        return new ViewHolderForFlickrImage(view);
    }

    @Override
    // Overriden method tu return the total count of objects(Photos in our case) to be displayed
    public int getItemCount() {

        return ((flickrPhotoList != null) && (flickrPhotoList.size() != 0) ? flickrPhotoList.size() : 0);
    }

    //Simply update the list of flickr Photo and call Data Set Changed Method of the adapter
    void loadNewData(List<FlickrPhoto> newFlickrPhotos) {
        flickrPhotoList = newFlickrPhotos;
        notifyDataSetChanged();
    }

    // Return the photo at the given position after checking it's not null or empty
    public FlickrPhoto getFlickrPhoto(int position) {
        return ((flickrPhotoList != null) && (flickrPhotoList.size() != 0) ? flickrPhotoList.get(position) : null);
    }

    static class ViewHolderForFlickrImage extends RecyclerView.ViewHolder {
        private static final String TAG = "ViewHolderForFlickrImage";
        ImageView smallImage = null;
        TextView title = null;

        //Constructor which intialised the thumbhnail and the title of the photo corresponding the received item view
        public ViewHolderForFlickrImage(View individualHolderView) {
            super(individualHolderView);

            this.smallImage = individualHolderView.findViewById(R.id.thumbnail);
            this.title = individualHolderView.findViewById(R.id.title);

        }
    }
}


