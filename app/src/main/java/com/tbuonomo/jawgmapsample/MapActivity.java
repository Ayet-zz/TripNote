package com.tbuonomo.jawgmapsample;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class MapActivity extends BottomNavActivity {

  private MapView mapView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


    setContentView(R.layout.activity_map);


    //Get the location
    //LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    //LocationListener locationListener = new MyLocationListener();
    //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

    //Get the mapview
    Mapbox.getInstance(this, BuildConfig.MAP_BOX_TOKEN);
    mapView = findViewById(R.id.mapView);
    mapView.setStyleUrl("https://tile.jawg.io/jawg-streets.json?access-token=" + BuildConfig.JAWG_API_KEY);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(MapboxMap mapboxMap) {
        // One way to add a marker view
        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(45.784265,4.869752))
                .title("You are here!")
                .snippet("Click on markers to show stories")
        );
      }
    });



      //Inflate and add navigation bar into the map activity
    LinearLayout navMap= (LinearLayout) findViewById(R.id.activity_map);
    LayoutInflater inflater = getLayoutInflater();
    View myLayout = inflater.inflate(R.layout.activity_bottom_nav, navMap, false);
    navMap.addView(myLayout);

      //Create the navigation bar listener
    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setSelectedItemId(R.id.navigation_map);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

  }

  @Override public void onStart() {
    super.onStart();
    mapView.onStart();
  }

  @Override public void onResume() {
    super.onResume();
    mapView.onResume();
  }

  @Override public void onPause() {
    super.onPause();
    mapView.onPause();
  }

  @Override public void onStop() {
    super.onStop();
    mapView.onStop();
  }

  @Override public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mapView.onSaveInstanceState(outState);
  }
}
