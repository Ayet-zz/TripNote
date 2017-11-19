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

public class ReadFileTask extends AsyncTask<Void, Void, Integer> {

    private static final String TAG = "ReadFileTask";

    private final OnTestListener onTestListener;

    private List<StoryRecyclerView> myDataSet;
    private final ProgressBar progressBar;

    private int nbStories;
    private int nbStoriesMax;

    public ReadFileTask(List<StoryRecyclerView> myDataSet, ProgressBar progressBar, OnTestListener onTestListener) {
        this.onTestListener=onTestListener;
        this.myDataSet=myDataSet;
        this.progressBar=progressBar;
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
                .addFilter(Filters.eq(SearchableField.MIME_TYPE, "image/jpeg"))
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

                                nbStories=0;
                                nbStoriesMax=metadataBuffer.getCount();

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
                                    final String latitude=item.getCustomProperties().get(new CustomPropertyKey("latitude", CustomPropertyKey.PUBLIC));
                                    final String longitude=item.getCustomProperties().get(new CustomPropertyKey("longitude", CustomPropertyKey.PUBLIC));
                                    final String author=item.getCustomProperties().get(new CustomPropertyKey("author", CustomPropertyKey.PUBLIC));
                                    final String date=item.getCustomProperties().get(new CustomPropertyKey("date",CustomPropertyKey.PUBLIC));
                                    final String comment=item.getCustomProperties().get(new CustomPropertyKey("comment",CustomPropertyKey.PUBLIC));
                                    final String authorComment=item.getCustomProperties().get(new CustomPropertyKey("authorComment",CustomPropertyKey.PUBLIC));
                                    final String dateComment=item.getCustomProperties().get(new CustomPropertyKey("dateComment",CustomPropertyKey.PUBLIC));
                                    //get the picture
                                    DriveId mId = item.getDriveId();
                                    DriveFile file = mId.asDriveFile ();
                                    Task<DriveContents> openFileTask =
                                            mConnectionDrive.getDriveResourceClient().openFile(file, DriveFile.MODE_READ_ONLY);

                                    openFileTask
                                            .continueWithTask(new Continuation<DriveContents, Task<Void>>() {
                                                @Override
                                                public Task<Void> then(@NonNull Task<DriveContents> task) throws Exception {

                                                    DriveContents contents = task.getResult();
                                                    // Process contents...

                                                    BitmapFactory.Options options = new BitmapFactory.Options();
                                                    options.inSampleSize = 4;

                                                    InputStream mInputStream = contents.getInputStream();
                                                    Bitmap bitmap1 = BitmapFactory.decodeStream(mInputStream, null, options);

                                                    //add title description image to myDataSet
                                                    myDataSet.add(new StoryRecyclerView(author,date, description, bitmap1,latitude,longitude, title, comment,authorComment,dateComment));

                                                    Task<Void> discardTask = mConnectionDrive.getDriveResourceClient().discardContents(contents);
                                                    return discardTask;
                                                }
                                            })
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    nbStories++;
                                                    if(nbStories == nbStoriesMax) {
                                                        onPostExecute(1);
                                                    }
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Handle failure
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
                        Log.e(TAG, "Error retrieving files", e);
                        // [END_EXCLUDE]
                    }
                });
        // [END query_results]
    }


}

