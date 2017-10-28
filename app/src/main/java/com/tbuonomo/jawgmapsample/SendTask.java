package com.tbuonomo.jawgmapsample;

import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by lothairelaeuffer on 26/10/2017.
 */

public class SendTask extends AsyncTask<Void, Void, Boolean> {

    private final OnSendListener onSendListener;
    private ImageView img;
    private String description;

    private ProgressBar progressBar;

    SendTask(ImageView img, String description, ProgressBar progress, OnSendListener onSendListener ){
        this.onSendListener = onSendListener;
        this.img = img;
        this.description = description;
        this.progressBar = progress;
    }


    @Override
    protected void onPreExecute() {
        // Show Progress bar
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        //TODO send picture


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        //Hide progress bar
        //Change activity or display error message
        if(success){
            onSendListener.OnSuccess();

        }else {
            onSendListener.OnFailure();
        }
        progressBar.setVisibility(View.GONE);
    }


    public interface OnSendListener{

        void OnSuccess();
        void OnFailure();

    }
}
