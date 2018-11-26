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

public class RecyclerViewAdapter_FLICKR extends RecyclerView.Adapter<RecyclerViewAdapter_FLICKR.ViewHolderForFlickrImage> {
    private static final String TAG = "FlickrRecyclerViewAdapt";
    private List<Photo> photoList;
    private Context content;

    public RecyclerViewAdapter_FLICKR(Context content, List<Photo> photoList) {
        this.photoList = photoList;
        this.content = content;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderForFlickrImage holder, int position) {
        Photo photoItem = photoList.get(position);
        Picasso.get().load(photoItem.getImage()).error(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(holder.smallImage);
        holder.title.setText(photoItem.getTitle());


    }

    @NonNull
    @Override
    public ViewHolderForFlickrImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false
        );
        return new ViewHolderForFlickrImage(view);
    }

    @Override
    public int getItemCount() {

        return ((photoList != null) && (photoList.size() != 0) ? photoList.size() : 0);
    }

    void loadNewData(List<Photo> newPhotos) {
        photoList = newPhotos;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int position) {
        return ((photoList != null) && (photoList.size() != 0) ? photoList.get(position) : null);
    }

    static class ViewHolderForFlickrImage extends RecyclerView.ViewHolder {
        private static final String TAG = "ViewHolderForFlickrImage";
        ImageView smallImage = null;
        TextView title = null;

        public ViewHolderForFlickrImage(View itemView) {
            super(itemView);

            this.smallImage = itemView.findViewById(R.id.thumbnail);
            this.title = itemView.findViewById(R.id.title);

        }
    }
}


