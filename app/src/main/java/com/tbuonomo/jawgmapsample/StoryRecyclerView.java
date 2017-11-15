package com.tbuonomo.jawgmapsample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.io.InputStream;

/**
 * Created by lothairelaeuffer on 13/11/2017.
 */

public class StoryRecyclerView {
    private  String latitude;
    private  String longitude;
    private String title;
    private String description;
    private Bitmap image;

    public StoryRecyclerView(String title, String description, Bitmap image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }
    public StoryRecyclerView(String title, String description) {
        this.title = title;
        this.description = description;
    }
    public StoryRecyclerView(String title, String description, Bitmap image,String latitude,String longitude) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.latitude=latitude;
        this.longitude=longitude;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
