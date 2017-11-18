package com.ayetlaeuffer.tripnote;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class SendActivity extends AppCompatActivity {

    public final static String TAG = "SendActivity";

    private Button sendBtn;
    private Button resetBtn;
    private ImageView img;
    private EditText description;
    private String author;
    private File mFile;
    private double longitude,latitude;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        img = findViewById(R.id.image);
        description = findViewById(R.id.description);
        sendBtn = findViewById(R.id.sendBtn);
        resetBtn = findViewById(R.id.resetBtn);
        mProgressBar = findViewById(R.id.send_progress);
        author = user.getDisplayName();

        mFile = new File (getExternalFilesDir(null), "currentPicture.jpg");
        Glide.with(this)
                .load(mFile)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                .into(img);

        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            LocationManager mLocationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
             longitude = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
             latitude = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
        }

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateFileTask( description.getText().toString(), author, String.valueOf(longitude), String.valueOf(latitude),mProgressBar, new CreateFileTask.OnCreateFileListener() {

                    @Override
                    public void OnSuccess() {
                        Intent intent = new Intent(SendActivity.this, BottomNavActivity.class);
                        startActivity(intent);
                    }
                    public void OnFailure(){
                        //Toast.makeText(getBaseContext(), R.string.wrong_password, Toast.LENGTH_LONG).show();
                        Log.d(TAG, "Fail");
                    }
                }).execute();
            }
        });

        resetBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

    }
}
