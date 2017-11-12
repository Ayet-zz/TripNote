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

        import android.app.Activity;
        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.util.Log;

        import com.google.android.gms.common.api.GoogleApi;
        import com.google.android.gms.drive.DriveFile;
        import com.google.android.gms.drive.DriveId;
        import com.google.android.gms.drive.DriveResourceClient;
        import com.google.android.gms.drive.Metadata;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;

        import java.util.Map;

/**
 * An activity to retrieve the metadata of a file.
 */
public class RetrieveMetadataActivity extends BaseDemoActivity {
    private static final String TAG = "PinFileActivity";
    private Intent resultData = new Intent();
    @Override
    protected void onDriveClientReady() {
        pickTextFile()
                .addOnSuccessListener(this,
                        new OnSuccessListener<DriveId>() {
                            @Override
                            public void onSuccess(DriveId driveId) {
                                retrieveMetadata(driveId.asDriveFile());
                            }
                        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "No file selected", e);
                        showMessage(getString(R.string.file_not_selected));
                        setResult(Activity.RESULT_CANCELED);
                        finish();
                    }
                });
    }
    private void retrieveMetadata(final DriveFile file) {
        // [START retrieve_metadata]
        Task<Metadata> getMetadataTask = getDriveResourceClient().getMetadata(file);
        getMetadataTask
                .addOnSuccessListener(this,
                        new OnSuccessListener<Metadata>() {
                            @Override
                            public void onSuccess(Metadata metadata) {
                                resultData.putExtra("MimeType",metadata.getMimeType());
                                resultData.putExtra("Title",metadata.getTitle());
                                resultData.putExtra("Description",metadata.getDescription());
                                if(metadata.getCustomProperties().get("latitude")!=null && metadata.getCustomProperties().get("longitude")!=null && metadata.getCustomProperties().get("ID")!=null) {
                                    resultData.putExtra("Latitude", metadata.getCustomProperties().get("latitude"));
                                    resultData.putExtra("ID", metadata.getCustomProperties().get("ID"));
                                    resultData.putExtra("Longitude", metadata.getCustomProperties().get("longitude"));
                                }
                                setResult(Activity.RESULT_OK,resultData);
                                finish();
                            }
                        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Unable to retrieve metadata", e);
                        showMessage(getString(R.string.read_failed));
                        setResult(Activity.RESULT_CANCELED);
                        finish();
                    }
                });
        // [END retrieve_metadata]
    }

}