package com.tbuonomo.jawgmapsample;

/*
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */



import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

/**
 * An activity to illustrate how to create a file.
 */
public class CreateFileActivity extends BaseDemoActivity {
    private static final String TAG = "CreateFileActivity";

    @Override
    protected void onDriveClientReady() {
        createFile();
    }

    private void createFile() {
        // [START create_file]
        final Task<DriveFolder> rootFolderTask = getDriveResourceClient().getRootFolder();
        final Task<DriveContents> createContentsTask = getDriveResourceClient().createContents();
        Tasks.whenAll(rootFolderTask, createContentsTask)
                .continueWithTask(new Continuation<Void, Task<DriveFile>>() {
                    @Override
                    public Task<DriveFile> then(@NonNull Task<Void> task) throws Exception {
                        DriveFolder parent = rootFolderTask.getResult();
                        DriveContents contents = createContentsTask.getResult();


                        OutputStream outputStream = contents.getOutputStream();
                        File mFile = new File(getExternalFilesDir(null), "currentPicture.jpg");

                        //Write the bitmap data
                        final Bitmap image = BitmapFactory.decodeFile(mFile.getPath());
                        ByteArrayOutputStream bitmapStream = new ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.PNG, 100, bitmapStream);
                        try {
                            outputStream.write(bitmapStream.toByteArray());
                        } catch (IOException e1) {
                            Log.i(TAG, "Unable to write file contents.");
                        }

                        Intent intent = getIntent();
                        String description = intent.getStringExtra("description");

                        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                                .setMimeType("image/jpeg")
                                .setTitle("Android Photo.png")
                                .setDescription(description)
                                .setCustomProperty(new CustomPropertyKey("Latitude", CustomPropertyKey.PUBLIC), "45.781")
                                .setCustomProperty(new CustomPropertyKey("Longitude", CustomPropertyKey.PUBLIC), "4.852")
                                //.setCustomProperty(new CustomPropertyKey("1", CustomPropertyKey.PUBLIC), "blabla1")
                                .build();

                        return getDriveResourceClient().createFile(parent, changeSet, contents);
                    }
                })
                .addOnSuccessListener(this,
                        new OnSuccessListener<DriveFile>() {
                            @Override
                            public void onSuccess(DriveFile driveFile) {
                                showMessage(getString(R.string.file_created,
                                        driveFile.getDriveId().encodeToString()));
                                finish();
                                Intent intent = new Intent(CreateFileActivity.this, BottomNavActivity.class);
                                startActivity(intent);
                            }
                        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Unable to create file", e);
                        showMessage(getString(R.string.file_create_error));
                        finish();
                    }
                });
        // [END create_file]
    }
}