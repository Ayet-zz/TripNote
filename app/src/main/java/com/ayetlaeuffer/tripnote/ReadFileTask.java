package com.ayetlaeuffer.tripnote;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.util.List;

/**
 * Created by lothairelaeuffer on 15/11/2017.
 */

public class ReadFileTask extends AsyncTask<Void, Void, Boolean> {


    private static final String TAG = "ReadFileTask";

    private final OnTestListener onTestListener;

    private List<StoryRecyclerView> myDataSet;
    //private final ProgressBar progressBar;

    public ReadFileTask(List<StoryRecyclerView> myDataSet, ProgressBar progressBar, OnTestListener onTestListener) {
        this.onTestListener=onTestListener;
        this.myDataSet=myDataSet;
        //this.progressBar=progressBar;
    }
    public ReadFileTask(List<StoryRecyclerView> myDataSet, OnTestListener onTestListener) {
        this.onTestListener=onTestListener;
        this.myDataSet=myDataSet;
    }

    @Override
    protected void onPreExecute() {
        // Show Progress bar
        //progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
            boolean connection=true;

            ReadFile mReadFile = new ReadFile(myDataSet);
            myDataSet = mReadFile.myDataSet;

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
            onTestListener.OnSuccess();
        }else {
            onTestListener.OnFailure();
        }
        //progressBar.setVisibility(View.GONE);
    }

    public interface OnTestListener{

        void OnSuccess();
        void OnFailure();

    }
}

