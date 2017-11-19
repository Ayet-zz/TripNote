package com.ayetlaeuffer.tripnote;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.metadata.CustomPropertyKey;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;


/**
 * Created by lothairelaeuffer on 15/11/2017.
 */

public class UpdateFileTask extends AsyncTask<Void, Void, Integer> {

    private static final String TAG = "ReadFileTask";

    private final OnTestListener onTestListener;

    private List<StoryRecyclerView> myDataSet;
    private final ProgressBar progressBar;
    private String title,comment,authorComment,dateComment;

    private int nbStories;
    private int nbStoriesMax;

    public UpdateFileTask(String authorComment,String dateComment,String title,String comment, ProgressBar progressBar, OnTestListener onTestListener) {
        this.onTestListener=onTestListener;
        this.progressBar=progressBar;
        this.title=title;
        this.comment=comment;
        this.authorComment=authorComment;
        this.dateComment=dateComment;
    }

    @Override
    protected void onPreExecute() {
        // Show Progress bar
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Integer doInBackground(Void... params) {
        Log.e(TAG,"doInBackground");
        listFiles();

        return 0;
    }

    @Override
    protected void onPostExecute(Integer success) {
        if(success == 0) {
            //return de doInBackground
            //do nothing: the task isn't finished
        }else if (success == 1){
            onTestListener.OnSuccess();
            progressBar.setVisibility(View.GONE);
        }else {
            onTestListener.OnFailure();
        }

    }

    public interface OnTestListener{

        void OnSuccess();
        void OnFailure();

    }

    private void listFiles() {

        final ConnectionDriveService mConnectionDrive = new ConnectionDriveService();

        Query query = new Query.Builder()
                .addFilter(Filters.and(Filters.eq(SearchableField.MIME_TYPE, "image/jpeg"),
                        Filters.eq(SearchableField.TITLE, title)
                        ))
                .build();

        // [START query_files]
        final Task<MetadataBuffer> queryTask = mConnectionDrive.getDriveResourceClient().query(query);
        // [END query_files]


        // [START query_results]
        queryTask
                .addOnSuccessListener(
                        new OnSuccessListener<MetadataBuffer>() {
                            @Override
                            public void onSuccess(MetadataBuffer metadataBuffer) {
                                // Handle results...
                                // [START_EXCLUDE]

                                    for (Iterator<Metadata> i = metadataBuffer.iterator(); i.hasNext();) {

                                        Metadata item = i.next();
                                    //get the picture
                                    DriveId mId = item.getDriveId();
                                    DriveFile file = mId.asDriveFile ();

                                        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                                                .setCustomProperty(new CustomPropertyKey("comment", CustomPropertyKey.PUBLIC),comment)
                                                .setCustomProperty(new CustomPropertyKey("authorComment", CustomPropertyKey.PUBLIC),authorComment)
                                                .setCustomProperty(new CustomPropertyKey("dateComment", CustomPropertyKey.PUBLIC),dateComment)
                                                .build();

                                        Task<Metadata> updateMetadataTask =mConnectionDrive.getDriveResourceClient().updateMetadata(file, changeSet);
                                        updateMetadataTask
                                                .addOnSuccessListener(
                                                        new OnSuccessListener<Metadata>() {
                                                            @Override
                                                            public void onSuccess(Metadata metadata) {
                                                                onPostExecute(1);
                                                            }
                                                        })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.e(TAG, "Unable to update metadata", e);
                                                        onPostExecute(-1);
                                                    }
                                                });

                                }
                                // [END_EXCLUDE]
                                metadataBuffer.release();

                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure...
                        // [START_EXCLUDE]
                        Log.e(TAG, "Error updating files", e);
                        // [END_EXCLUDE]
                    }
                });
        // [END query_results]
    }


}

