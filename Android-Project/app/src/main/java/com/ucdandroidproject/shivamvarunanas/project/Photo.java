package com.ucdandroidproject.shivamvarunanas.project;

import java.io.Serializable;

/*Created by Shivam Rathore
* Represents the Photo Object
* */
class Photo implements Serializable {


    private String photoTitle;
    private String photoAuthor;
    private String photoAuthorId;
    private String photoLink;
    private String photoTags;
    private String photoImage;

    public Photo(String title, String author, String authorId, String link, String tags, String image) {
        photoTitle = title;
        photoAuthor = author;
        photoAuthorId = authorId;
        photoLink = link;
        photoTags = tags;
        photoImage = image;
    }

    String getTitle() {
        return photoTitle;
    }

    String getAuthor() {
        return photoAuthor;
    }

    String getAuthorId() {
        return photoAuthorId;
    }

    String getLink() {
        return photoLink;
    }

    String getTags() {
        return photoTags;
    }

    String getImage() {
        return photoImage;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "photoTitle='" + photoTitle + '\'' +
                ", photoAuthor='" + photoAuthor + '\'' +
                ", photoAuthorId='" + photoAuthorId + '\'' +
                ", photoLink='" + photoLink + '\'' +
                ", photoTags='" + photoTags + '\'' +
                ", photoImage='" + photoImage + '\'' +
                '}';
    }
}
