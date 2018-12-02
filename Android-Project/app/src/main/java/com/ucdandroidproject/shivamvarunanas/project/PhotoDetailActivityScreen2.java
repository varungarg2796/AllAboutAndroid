package com.ucdandroidproject.shivamvarunanas.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/*
CREATED BY SHIVAM RATHORE
This class is used to represent the activity used display the enlarged Photo
*/

public class PhotoDetailActivityScreen2 extends TopActivityScreen2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        enableBackToHomeToolbar(false);

        Intent intent = getIntent();
        FlickrPhoto flickrPhoto = (FlickrPhoto) intent.getSerializableExtra("PHOTO_TRANSFER");
        if (flickrPhoto != null) {
            TextView photoTitle = findViewById(R.id.photo_title);
            photoTitle.setText("Title: " + flickrPhoto.getTitle());

            TextView photoTags = findViewById(R.id.photo_tags);
            photoTags.setText("Tags: " + flickrPhoto.getTags());

            TextView photoAuthor = findViewById(R.id.photo_author);
            photoAuthor.setText(flickrPhoto.getAuthor());

            ImageView photoImage = findViewById(R.id.photo_image);

            Picasso.get().load(flickrPhoto.getLink()).error(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(photoImage);
        }
    }

}
