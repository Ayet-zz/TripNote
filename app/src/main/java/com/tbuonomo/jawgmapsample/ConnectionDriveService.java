package com.tbuonomo.jawgmapsample;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.HashSet;
import java.util.Set;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

/**
 * Created by lothairelaeuffer on 14/11/2017.
 */

public abstract class ConnectionDriveService {
    private static final String TAG = "BaseDriveActivity";

    /**
     * Request code for google sign-in
     */
    protected static final int REQUEST_CODE_SIGN_IN = 0;

    /**
     * Request code for the Drive picker
     */
    protected static final int REQUEST_CODE_OPEN_ITEM = 1;

    /**
     * Handles high-level drive functions like sync
     */
    private DriveClient mDriveClient;

    /**
     * Handle access to Drive resources/files.
     */
    private DriveResourceClient mDriveResourceClient;

    /**
     * Tracks completion of the drive picker
     */
    private TaskCompletionSource<DriveId> mOpenItemTaskSource;



    public ConnectionDriveService(){
        signIn();
    }


    /**
     * Starts the sign-in process and initializes the Drive client.
     */
    protected void signIn() {

        Set<Scope> requiredScopes = new HashSet<>(2);
        requiredScopes.add(Drive.SCOPE_FILE);
        requiredScopes.add(Drive.SCOPE_APPFOLDER);
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (signInAccount != null && signInAccount.getGrantedScopes().containsAll(requiredScopes)) {
            initializeDriveClient(signInAccount);
        } else {
            GoogleSignInOptions signInOptions =
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestScopes(Drive.SCOPE_FILE)
                            .requestScopes(Drive.SCOPE_APPFOLDER)
                            .build();
            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), signInOptions);
            //TODO
            //startActivityForResult(googleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);
        }

    }

    /**
     * Continues the sign-in process, initializing the Drive clients with the current
     * user's account.
     */
    private void initializeDriveClient(GoogleSignInAccount signInAccount) {
        mDriveClient = Drive.getDriveClient(getApplicationContext(), signInAccount);
        mDriveResourceClient = Drive.getDriveResourceClient(getApplicationContext(), signInAccount);
//        onDriveClientReady();
    }

    /**
     * Prompts the user to select a text file using OpenFileActivity.
     *
     * @return Task that resolves with the selected item's ID.
     */
//    protected Task<DriveId> pickTextFile() {
//        OpenFileActivityOptions openOptions =
//                new OpenFileActivityOptions.Builder()
//                        .setSelectionFilter(Filters.eq(SearchableField.MIME_TYPE, "text/plain"))
//                        .setActivityTitle(getString(R.string.select_file))
//                        .build();
//        return pickItem(openOptions);
//    }

    /**
     * Prompts the user to select a folder using OpenFileActivity.
     *
     * @return Task that resolves with the selected item's ID.
     */
//    protected Task<DriveId> pickFolder() {
//        OpenFileActivityOptions openOptions =
//                new OpenFileActivityOptions.Builder()
//                        .setSelectionFilter(
//                                Filters.eq(SearchableField.MIME_TYPE, DriveFolder.MIME_TYPE))
//                        .setActivityTitle(getString(R.string.select_folder))
//                        .build();
//        return pickItem(openOptions);
//    }

    /**
     * Prompts the user to select a folder using OpenFileActivity.
     *
     * @param openOptions Filter that should be applied to the selection
     * @return Task that resolves with the selected item's ID.
     */
//    private Task<DriveId> pickItem(OpenFileActivityOptions openOptions) {
//        mOpenItemTaskSource = new TaskCompletionSource<>();
//        getDriveClient()
//                .newOpenFileActivityIntentSender(openOptions)
//                .continueWith(new Continuation<IntentSender, Void>() {
//                    @Override
//                    public Void then(@NonNull Task<IntentSender> task) throws Exception {
//                        startIntentSenderForResult(
//                                task.getResult(), REQUEST_CODE_OPEN_ITEM, null, 0, 0, 0);
//                        return null;
//                    }
//                });
//        return mOpenItemTaskSource.getTask();
//    }

    /**
     * Shows a toast message.
     */
//    protected void showMessage(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//    }

    /**
     * Called after the user has signed in and the Drive client has been initialized.
     */
    protected abstract void onDriveClientReady();

    protected DriveClient getDriveClient() {
        return mDriveClient;
    }

    protected DriveResourceClient getDriveResourceClient() {
        return mDriveResourceClient;
    }
}

