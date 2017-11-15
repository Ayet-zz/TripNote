package com.ayetlaeuffer.tripnote;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by aurel on 25/10/2017.
 */
public class LoginTask extends AsyncTask<Void, Void, Boolean> {

    private static final String CONNECT_URL = "https...";
    private final OnLoginListener onLoginListener;
    private final String username;
    private final String password;
    private final ProgressBar progressBar;

    public LoginTask(String username, String password, ProgressBar progress, OnLoginListener onLoginListener) {
        this.onLoginListener=onLoginListener;
        this.username=username;
        this.password=password;
        this.progressBar=progress;
    }

    @Override
    protected void onPreExecute() {
        // Show Progress bar
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        boolean connection=false;
        if("admin".equals(username)&&"admin".equals(password)) {
            connection=true;
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        //Hide progress bar
        //Change activity or display error message
        if(success){
            onLoginListener.OnSuccess();
            //Intent intent = new Intent(LoginActivity.this, MyActivity.class);

        }else {
           onLoginListener.OnFailure();
    }
        progressBar.setVisibility(View.GONE);
    }

    public interface OnLoginListener{

            void OnSuccess();
            void OnFailure();

    }
}

