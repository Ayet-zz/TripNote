package com.tbuonomo.jawgmapsample;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.UUID;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static java.security.AccessController.getContext;

public class SendActivity extends AppCompatActivity {

    private Button sendBtn;
    private Button resetBtn;
    private ImageView img;
    private EditText description;
    private String uniqueID;
    private File mFile;
    private double longitude,latitude;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        img = (ImageView)findViewById(R.id.image);
        description = (EditText)findViewById(R.id.description);
        sendBtn = (Button)findViewById(R.id.sendBtn);
        resetBtn = (Button) findViewById(R.id.resetBtn);
        mProgressBar = (ProgressBar)findViewById(R.id.send_progress);
        uniqueID = UUID.randomUUID().toString();

        mFile = new File (getExternalFilesDir(null), "currentPicture.jpg");
        Glide.with(this)
                .load(mFile)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(img);
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            LocationManager mLocationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
             longitude = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
             latitude = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
        }

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendActivity.this, CreateFileActivity.class);
                intent.putExtra("description", description.getText().toString());
                intent.putExtra("uniqueID",uniqueID);
                intent.putExtra("longitude",String.valueOf(longitude));
                intent.putExtra("latitude",String.valueOf(latitude));

                startActivity(intent);
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
