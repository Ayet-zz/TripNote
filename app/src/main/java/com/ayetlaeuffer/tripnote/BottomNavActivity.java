package com.ayetlaeuffer.tripnote;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class BottomNavActivity extends AppCompatActivity {

    private static final String TAG = "BottomNavActivity";

    CameraFragment mCameraFragment;
    MapFragment mMapFragment;
    StoriesFragment mStoriesFragment;


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
                    switchToStoriesFragment();
                    return true;
            }
            return false;
        }


        public void switchToCameraFragment() {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content, mCameraFragment).commit();
        }
        public void switchToStoriesFragment() {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content,mStoriesFragment).commit();
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
        mStoriesFragment=new StoriesFragment();


        //DEFAULT
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.content, mMapFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        getMenuInflater().inflate(R.menu.action_bar, menu);


        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.action_disconnect:
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(BottomNavActivity.this, FirebaseLogin.class);
                            startActivity(intent);
                        }
                    });
            return(true);
        case R.id.delete_account:
            AuthUI.getInstance()
                    .delete(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(BottomNavActivity.this, FirebaseLogin.class);
                            startActivity(intent);
                        }
                    });
            //add the function to perform here
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }



}



