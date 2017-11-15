package com.ayetlaeuffer.tripnote;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.LOCATION_SERVICE;


public class MapFragment extends Fragment {

    private static final int MY_REQUEST_ID = 1;
    private MapView mapView;
    private FloatingActionButton cameraRepositioning;
    private List<MarkerOptions> markerOptionsList=new ArrayList<MarkerOptions>();
    private List<StoryRecyclerView> myDataSet = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Mapbox.getInstance(getContext(), BuildConfig.MAP_BOX_TOKEN);
        //get the variable from the frameLayout
        mapView = view.findViewById(R.id.mapView);
        cameraRepositioning=view.findViewById(R.id.camera_repositioning);
        //set the configuration
        mapView.setStyleUrl("https://tile.jawg.io/jawg-streets.json?access-token=" + BuildConfig.JAWG_API_KEY);
        mapView.onCreate(savedInstanceState);

        if(myDataSet != null){
            new ReadFileTask( myDataSet, new ReadFileTask.OnTestListener() {
                @Override
                public void OnSuccess() {
                    for (StoryRecyclerView data:myDataSet) {
                        markerOptionsList.add(new MarkerOptions().position(new LatLng(Double.parseDouble(data.getLatitude()),Double.parseDouble(data.getLongitude())))
                                .title(data.getTitle()));
                    }

                }
                public void OnFailure(){
                    //Toast.makeText(getBaseContext(), R.string.wrong_password, Toast.LENGTH_LONG).show();
                }
            }).execute();



            //Check if we have the permission and create a location manager
            if (ContextCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
                LocationManager mLocationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

                 final MapLocationListener mLocationListener = new MapLocationListener(mapView,markerOptionsList){
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        if(!(marker.getTitle().equals("You are here!"))) {
                           if(markerOptionsList.contains(marker))
                           {
                               Intent intent = new Intent(getContext(), MediaActivity.class);
                               intent.putExtra("bitMap",myDataSet.get(markerOptionsList.indexOf(marker)).getImage());
                               intent.putExtra("title",myDataSet.get(markerOptionsList.indexOf(marker)).getTitle());
                               intent.putExtra("description",myDataSet.get(markerOptionsList.indexOf(marker)).getDescription());
                               startActivity(intent);
                               return true;
                           }

                        }
                       return false;
                    }
                };
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, mLocationListener);
                cameraRepositioning.setOnClickListener(mLocationListener);

            }
        }

    }



}
