package com.tbuonomo.jawgmapsample;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

/**
 * Created by lothairelaeuffer on 15/11/2017.
 */

public class TestTask extends AsyncTask<Void, Void, Boolean> {


    private static final String TAG = "TestTask";

    private final OnTestListener onTestListener;

    private List<StoryRecyclerView> myDataSet;
    //private final ProgressBar progressBar;

    public TestTask(List<StoryRecyclerView> myDataSet, OnTestListener onTestListener) {
            this.onTestListener=onTestListener;
            this.myDataSet=myDataSet;
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "preexecute");
    }

    @Override
    protected Boolean doInBackground(Void... params) {
            boolean connection=true;

            TestActivity mTestActivity = new TestActivity(myDataSet);
            myDataSet = mTestActivity.myDataSet;

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
    }

    public interface OnTestListener{

        void OnSuccess();
        void OnFailure();

    }
}

