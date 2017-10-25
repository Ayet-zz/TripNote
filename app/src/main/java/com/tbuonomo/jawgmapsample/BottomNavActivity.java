package com.tbuonomo.jawgmapsample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class BottomNavActivity extends AppCompatActivity {

    protected BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    Intent home = new Intent(getBaseContext(), MapActivity.class);
                    startActivity(home);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_camera:
                    Intent camera = new Intent(getBaseContext(), CameraActivity.class);
                    startActivity(camera);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_stories:
                    Intent story = new Intent(getBaseContext(), StoriesActivity.class);
                    startActivity(story);
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);


    }

}
