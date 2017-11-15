package com.ayetlaeuffer.tripnote;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;


/**
 * Created by lothairelaeuffer on 15/11/2017.
 */

public class CreateFileTask extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = "CreateFileTask";

    private String description;
    private String uniqueID;
    private String longitude;
    private String latitude;
    private ProgressBar progressBar;

    private final OnCreateFileListener onCreateFileListener;


    public CreateFileTask(String description, String uniqueID, String longitude, String latitude, ProgressBar progressBar, OnCreateFileListener onCreateFileListener) {
        //super();
        this.onCreateFileListener=onCreateFileListener;
        this.description = description;
        this.uniqueID = uniqueID;
        this.longitude = longitude;
        this.latitude = latitude;
        this.progressBar=progressBar;
    }


    @Override
    protected void onPreExecute() {
        // Show Progress bar
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        boolean connection=true;

        CreateFile mCreateFile = new CreateFile(description, uniqueID, longitude, latitude);
        mCreateFile.createFile();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if(success){
            onCreateFileListener.OnSuccess();
        }else {
            onCreateFileListener.OnFailure();
        }
        progressBar.setVisibility(View.GONE);
    }

    public interface OnCreateFileListener{

        void OnSuccess();
        void OnFailure();

    }
}

