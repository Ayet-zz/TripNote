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

    public StoryRecyclerView(String author, String date,String description, Bitmap image) {
        this.author = author;
        this.description = description;
        this.image = image;
        this.date=date;
    }

    public StoryRecyclerView(String author,String date, String description, Bitmap image,String latitude,String longitude) {
        this.author = author;
        this.description = description;
        this.image = image;
        this.latitude=latitude;
        this.longitude=longitude;
        this.date=date;
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
