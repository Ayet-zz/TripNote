package com.tbuonomo.jawgmapsample;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aurel on 20/10/2017.
 */

public class StoriesActivity extends BottomNavActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_stories);

            RecyclerView recyclerView =(RecyclerView)findViewById(R.id.stories_recycler_view) ;


            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            recyclerView.setHasFixedSize(true);

            // use a linear layout manager
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            // specify an adapter
            List<String> myDataSet=new ArrayList<String>();
            myDataSet.add("Jean");
            myDataSet.add("Jacques");
            myDataSet.add("Antoine");
            myDataSet.add("Robin");
            myDataSet.add("Ghali");
            myDataSet.add("Aurélien");
            ItemAdapter adapter = new ItemAdapter(myDataSet);
            recyclerView.setAdapter(adapter);

            //Inflate and add navigation bar into the stories activity
            LinearLayout navStories= (LinearLayout) findViewById(R.id.activity_stories);
            LayoutInflater inflater = getLayoutInflater();
            View myLayout = inflater.inflate(R.layout.activity_bottom_nav, navStories, false);
            navStories.addView(myLayout);
            //Create the navigation listener
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setSelectedItemId(R.id.navigation_stories);
//            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }

    @Override public void onStart() {
        super.onStart();
    }

    @Override public void onResume() {
        super.onResume();

    }

    @Override public void onPause() {
        super.onPause();
    }

    @Override public void onStop() {
        super.onStop();
    }

    @Override public void onLowMemory() {
        super.onLowMemory();
    }

    @Override protected void onDestroy() {
        super.onDestroy();

    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    }



