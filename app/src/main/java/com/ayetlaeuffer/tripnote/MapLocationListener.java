package com.ayetlaeuffer.tripnote;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.List;

/**
 * Created by aurel on 25/10/2017.
 */


public class MapLocationListener implements LocationListener, View.OnClickListener,MapboxMap.OnMarkerClickListener {

    private MapView mapView;
    private List<MarkerOptions> markerOptionsList;
    private Location lastKnownLocation;

    MapLocationListener(MapView mapView,List<MarkerOptions> markerOptionsList) {
        this.mapView=mapView;
        this.markerOptionsList=markerOptionsList;
    }
    @Override
    public void onLocationChanged(final Location loc) {
        lastKnownLocation=loc;
         this.mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                // One way to add a marker view
                mapboxMap.setLatLng(new LatLng(loc.getLatitude(),loc.getLongitude()));
                //Delete old markers
                for (Marker marker:mapboxMap.getMarkers()) {
                    mapboxMap.removeMarker(marker);
                }
                //Add marker position
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(loc))
                        .title("You are here!")
                        .snippet("Click on markers to show stories")
                        .icon(IconFactory.getInstance(mapView.getContext()).fromResource(R.drawable.ic_location))
                );
                //Add markers of the list under 5km
                for (MarkerOptions markerOptions:markerOptionsList) {
                    float distance[]= new float[1];
                    Location.distanceBetween(loc.getLatitude(),loc.getLongitude(),markerOptions.getPosition().getLatitude(),markerOptions.getPosition().getLongitude(),distance);
                    if(distance[0]<5000) // choix des 5 km
                        mapboxMap.addMarker(markerOptions);
                }
                mapboxMap.setOnMarkerClickListener(MapLocationListener.this);
            }
        });

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onClick(View view) {
        this.mapView.getMapAsync(new OnMapReadyCallback(){
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                if(lastKnownLocation!=null) {
                    mapboxMap.setCameraPosition(new CameraPosition.Builder()
                            .target(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()))
                            .tilt(0)
                            .zoom(11.0f)
                            .build()

                    );
                }
            }
        });
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }
}

