package com.ayetlaeuffer.tripnote;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.metadata.CustomPropertyKey;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;


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

        createFile();

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

    public void createFile() {

        final ConnectionDriveService mConnectionDrive = new ConnectionDriveService();

        final Task<DriveFolder> rootFolderTask = mConnectionDrive.getDriveResourceClient().getRootFolder();
        final Task<DriveContents> createContentsTask = mConnectionDrive.getDriveResourceClient().createContents();
        Tasks.whenAll(rootFolderTask, createContentsTask)
                .continueWithTask(new Continuation<Void, Task<DriveFile>>() {
                    @Override
                    public Task<DriveFile> then(@NonNull Task<Void> task) throws Exception {
                        DriveFolder parent = rootFolderTask.getResult();
                        DriveContents contents = createContentsTask.getResult();

                        OutputStream outputStream = contents.getOutputStream();
                        File mFile = new File(getApplicationContext().getExternalFilesDir(null), "currentPicture.jpg");

                        //Write the bitmap data
                        Bitmap image = BitmapFactory.decodeFile(mFile.getPath());
                        image = rotateBitmap(image, 90);
                        ByteArrayOutputStream bitmapStream = new ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.JPEG, 10, bitmapStream);
                        try {
                            outputStream.write(bitmapStream.toByteArray());
                        } catch (IOException e1) {
                            Log.i(TAG, "Unable to write file contents.");
                        }


                        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                                .setMimeType("image/jpeg")
                                .setTitle(uniqueID+"'s story")
                                .setDescription(description)
                                .setCustomProperty(new CustomPropertyKey("latitude", CustomPropertyKey.PUBLIC),latitude)
                                .setCustomProperty(new CustomPropertyKey("longitude", CustomPropertyKey.PUBLIC), longitude)
                                .build();

                        return mConnectionDrive.getDriveResourceClient().createFile(parent, changeSet, contents);
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<DriveFile>() {
                    @Override
                    public void onSuccess(DriveFile driveFile) {
                        Toast.makeText(getApplicationContext(), R.string.story_send, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Unable to create file", e);
                        Toast.makeText(getApplicationContext(), R.string.story_send_fail, Toast.LENGTH_LONG).show();
                    }
                });
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

}

