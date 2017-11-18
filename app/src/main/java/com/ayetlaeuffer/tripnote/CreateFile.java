package com.ayetlaeuffer.tripnote;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.util.Log;

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

public class CreateFile extends ConnectionDriveService {
    private static final String TAG = "FileActivity";

    private String description;
    private String uniqueID;
    private String longitude;
    private String latitude;

    public CreateFile(String description, String uniqueID, String longitude, String latitude){
        this.description = description;
        this.uniqueID = uniqueID;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void createFile() {
        final Task<DriveFolder> rootFolderTask = getDriveResourceClient().getRootFolder();
        final Task<DriveContents> createContentsTask = getDriveResourceClient().createContents();
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

                        return getDriveResourceClient().createFile(parent, changeSet, contents);
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<DriveFile>() {
                            @Override
                            public void onSuccess(DriveFile driveFile) {
                                Log.d(TAG,"file created");
                                //showMessage(getString(R.string.file_created,
                                 //       driveFile.getDriveId().encodeToString()));
                                //finish();
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Unable to create file", e);
                        //showMessage(getString(R.string.file_create_error));
                        //finish();
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