package com.ayetlaeuffer.tripnote;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aurel on 20/10/2017.
 */

public class MediaActivity extends AppCompatActivity {

    private static final String TAG = "MediaActivity";

        private List<StoryRecyclerView> myDataSet;
        private RecyclerView recyclerView;
        private StoryAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_media);

            recyclerView = findViewById(R.id.media_recycler_view) ;
            FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            recyclerView.setHasFixedSize(true);

            // use a linear layout manager
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            // specify an adapter
            myDataSet=new ArrayList<StoryRecyclerView>();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getBaseContext().openFileInput("myImage"));
                myDataSet.add(new StoryRecyclerView(getIntent().getStringExtra("title"),getIntent().getStringExtra("description"),bitmap));
                adapter = new StoryAdapter(myDataSet, Glide.with(this));
                recyclerView.setAdapter(adapter);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);
                    if ((dy > 0||dy<0) && floatingActionButton.getVisibility() == View.VISIBLE) {
                        floatingActionButton.hide();
                    }
                }
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView,newState);
                    FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);
                    if(newState==0&& floatingActionButton.getVisibility() != View.VISIBLE)
                    {
                        floatingActionButton.show();
                    }
                }

            });
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
        //TODO delete the following lines if MediaActivity is ok
//        Log.e(TAG,"onStop");
//        if (myDataSet != null && recyclerView != null) {
//            Log.d(TAG,"inside if");
//            recyclerView.setAdapter(adapter);
//            recyclerView = null;
//        }
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
