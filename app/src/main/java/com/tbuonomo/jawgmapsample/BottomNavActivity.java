package com.tbuonomo.jawgmapsample;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;



public class BottomNavActivity extends AppCompatActivity {

    CameraFragment mCameraFragment;
    MapFragment mMapFragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    switchToMapFragment();
                    return true;
                case R.id.navigation_camera:
                    switchToCameraFragment();
                    return true;
                case R.id.navigation_stories:
                    switchToNoFragment();
                    return true;
            }
            return false;
        }


        public void switchToCameraFragment() {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content, mCameraFragment).commit();
        }
        public void switchToNoFragment() {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().remove(mCameraFragment).commit();
            manager.beginTransaction().remove(mMapFragment).commit();
        }
        public void switchToMapFragment() {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content, mMapFragment).commit();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //MAP
        mMapFragment = new MapFragment();

        //CAMERA
        mCameraFragment = new CameraFragment();

        //STORY



        //DEFAULT
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content, mMapFragment).commit();
    }



}



