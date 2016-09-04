package com.example.mallannius.fitnessgps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class showMap extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    double lat;
    double lon;
    double stop_lat;
    double stop_lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showmap);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            lat = bundle.getDouble("start_lat");
            lon = bundle.getDouble("start_lon");
            stop_lat = bundle.getDouble("stop_lat");
            stop_lon = bundle.getDouble("stop_lon");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng start = new LatLng(lat, lon);
        LatLng stop = new LatLng(stop_lat, stop_lon);

        //This method allow a marker to be placed in a position
        mMap.addMarker(new MarkerOptions().position(start).title("Start"));
        mMap.addMarker(new MarkerOptions().position(stop).title("Stop"));

        //This method positions the centre of the map at the position
        mMap.moveCamera(CameraUpdateFactory.newLatLng(start));

        //This method sets the zoom level of the view....
        mMap.moveCamera(CameraUpdateFactory.zoomTo(11));

        /// This allows the user to zoom in and out of the map
        mMap.getUiSettings().setZoomControlsEnabled(true);


    }
}