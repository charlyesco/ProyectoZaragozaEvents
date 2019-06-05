package com.example.carlos.proyectoevents;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String coordenada0;
    String coordenada1;
    Double c0;
    Double c1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (status == ConnectionResult.SUCCESS) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } else {
            Dialog dialog = (Dialog) GooglePlayServicesUtil.getErrorDialog(status, (Activity) getApplicationContext(), 10);
            dialog.show();
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //llegan las coordenadas
        Bundle extras = getIntent().getExtras();
        coordenada0 = extras.getString(EventosAsyncTask.COORD0);
        coordenada1 = extras.getString(EventosAsyncTask.COORD1);
        String title = extras.getString(EventosAsyncTask.TITLE);
        c0 = Double.parseDouble(coordenada0);
        c1 = Double.parseDouble(coordenada1);

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings ui = mMap.getUiSettings();
        ui.setZoomControlsEnabled(true);
        LatLng sydney = new LatLng(c1, c0);
        mMap.addMarker(new MarkerOptions().position(sydney).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        float zoomLevel = 16;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
    }
}
