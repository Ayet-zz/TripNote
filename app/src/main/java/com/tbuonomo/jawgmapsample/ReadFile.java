package com.tbuonomo.jawgmapsample;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.SearchableField;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;


/**
 * An activity to illustrate how to query files.
 */
public class ReadFile extends ConnectionDriveService {

    private static final String TAG = "ReadFile";

    private MetadataBuffer metadataBuffer;
    public List<StoryRecyclerView> myDataSet;


    public ReadFile(List<StoryRecyclerView> myDataSet){
        super();
        this.myDataSet=myDataSet;
        listFiles();
    }


    //@Override
    //protected void onDriveClientReady() {
    //    listFiles();
    //}

    private void listFiles() {
        Query query = new Query.Builder()
                .addFilter(Filters.eq(SearchableField.MIME_TYPE, "image/jpeg"))
                .build();

        // [START query_files]
        Task<MetadataBuffer> queryTask = getDriveResourceClient().query(query);
        // [END query_files]


        // [START query_results]
        queryTask
                .addOnSuccessListener(
                        new OnSuccessListener<MetadataBuffer>() {
                            @Override
                            public void onSuccess(MetadataBuffer metadataBufferBis) {
                                metadataBuffer=metadataBufferBis;
                                // Handle results...
                                // [START_EXCLUDE]

                                for (Iterator<Metadata> i = metadataBuffer.iterator(); i.hasNext();) {
                                    Metadata item = i.next();

                                    //get the title
                                    final String title = item.getTitle();

                                    //get the description
                                    final String description;
                                    if (item.getDescription() != null) {
                                        description = item.getDescription();
                                    }
                                    else{
                                        description = "";
                                    }

                                    //get the picture
                                    DriveId mId = item.getDriveId();
                                    DriveFile file = mId.asDriveFile ();
                                    Task<DriveContents> openFileTask =
                                            getDriveResourceClient().openFile(file, DriveFile.MODE_READ_ONLY);
                                    openFileTask
                                            .continueWithTask(new Continuation<DriveContents, Task<Void>>() {
                                                @Override
                                                public Task<Void> then(@NonNull Task<DriveContents> task) throws Exception {
                                                    DriveContents contents = task.getResult();
                                                    // Process contents...
                                                    InputStream mInputStream = contents.getInputStream();
                                                    Bitmap bitmap1 = BitmapFactory.decodeStream(mInputStream);

                                                    //add title description image to myDataSet
                                                    myDataSet.add(new StoryRecyclerView(title, description, bitmap1));

                                                    Task<Void> discardTask = getDriveResourceClient().discardContents(contents);
                                                    return discardTask;
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Handle failure
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
                        Log.e(TAG, "Error retrieving files", e);
                        //showMessage(getString(R.string.query_failed));
                        //finish();
                        // [END_EXCLUDE]
                    }
                });
        // [END query_results]
    }


}