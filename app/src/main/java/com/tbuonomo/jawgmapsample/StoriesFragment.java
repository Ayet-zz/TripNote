package com.tbuonomo.jawgmapsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by aurel on 25/10/2017.
 */

public class StoriesFragment extends Fragment{
    public final static String TAG = "StoriesFragment";

    private List<StoryRecyclerView> myDataSet;
    private StoryAdapter adapter;

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_stories, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        RecyclerView recyclerView =(RecyclerView)view.findViewById(R.id.stories_recycler_view) ;
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        recyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//
//        // specify an adapter
//        List<MediaObjectRecyclerView> myDataSet=new ArrayList<>();
//        myDataSet.add(new MediaObjectRecyclerView("Jean"));
//        myDataSet.add(new MediaObjectRecyclerView("Jacques"));
//        myDataSet.add(new MediaObjectRecyclerView("Salome"));
//        myDataSet.add(new MediaObjectRecyclerView("Robin"));
//        myDataSet.add(new MediaObjectRecyclerView("Ghali"));
//        myDataSet.add(new MediaObjectRecyclerView("Aurelien"));
//        ItemAdapter adapter = new ItemAdapter(myDataSet);
//        recyclerView.setAdapter(adapter);


        recyclerView =(RecyclerView)view.findViewById(R.id.stories_recycler_view) ;
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        myDataSet=new ArrayList<>();



        new ReadFileTask( myDataSet, new ReadFileTask.OnTestListener() {
            @Override
            public void OnSuccess() {

                // specify an adapter
                adapter = new StoryAdapter(myDataSet);
                recyclerView.setAdapter(adapter);
            }
            public void OnFailure(){
                //Toast.makeText(getBaseContext(), R.string.wrong_password, Toast.LENGTH_LONG).show();
                Log.d(TAG, "Fail");
            }
        }).execute();










    }

}