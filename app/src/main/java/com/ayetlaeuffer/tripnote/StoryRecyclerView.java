package com.ayetlaeuffer.tripnote;

import android.graphics.Bitmap;

/**
 * Created by lothairelaeuffer on 13/11/2017.
 */

public class StoryRecyclerView {
    private String latitude;
    private String longitude;
    private String author;
    private String description;
    private Bitmap image;
    private String date;
    private String title;
    private String comment;
    private String authorComment;
    private String dateComment;

    public StoryRecyclerView(String author, String date,String description, Bitmap image,String title,String comment,String authorComment,String dateComment) {
        this.author = author;
        this.description = description;
        this.image = image;
        this.date=date;
        this.title=title;
        this.comment=comment;
        this.authorComment=authorComment;
        this.dateComment=dateComment;
    }

    public StoryRecyclerView(String author,String date, String description, Bitmap image,String latitude,String longitude,String title,String comment,String authorComment,String dateComment) {
        this.author = author;
        this.description = description;
        this.image = image;
        this.latitude=latitude;
        this.longitude=longitude;
        this.date=date;
        this.title=title;
        this.comment=comment;
        this.authorComment=authorComment;
        this.dateComment=dateComment;
    }


    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
    }
    public String getAuthorComment() {
        return authorComment;
    }

    public String getDateComment() {
        return dateComment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }


    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
