package com.tbuonomo.jawgmapsample;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aurel on 20/10/2017.
 */

public class MediaActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_media);

            RecyclerView recyclerView =(RecyclerView)findViewById(R.id.media_recycler_view) ;
            //FloatingActionButton=(FloatingActionButton)findViewById(R.id.floating_action_button);


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



