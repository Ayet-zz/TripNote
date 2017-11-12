package com.tbuonomo.jawgmapsample;

import android.os.Bundle;
import android.provider.MediaStore;
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
            FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.floating_action_button);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            recyclerView.setHasFixedSize(true);

            // use a linear layout manager
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            // specify an adapter
            List<MediaObjectRecyclerView> myDataSet=new ArrayList<MediaObjectRecyclerView>();
            myDataSet.add(new MediaObjectRecyclerView(getIntent().getStringExtra("Title"),"https://www.thesun.co.uk/wp-content/uploads/2016/08/nintchdbpict000256190292.jpg?w=960"));
            myDataSet.add(new MediaObjectRecyclerView(getIntent().getStringExtra("Description")));
            ItemAdapter adapter = new ItemAdapter(myDataSet);
            recyclerView.setAdapter(adapter);
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
                    FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.floating_action_button);
                    if ((dy > 0||dy<0) && floatingActionButton.getVisibility() == View.VISIBLE) {
                        floatingActionButton.hide();
                    }
                }
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView,newState);
                    FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.floating_action_button);
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



