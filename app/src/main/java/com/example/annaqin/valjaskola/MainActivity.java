package com.example.annaqin.valjaskola;

import android.*;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

// test in github
public class MainActivity extends FragmentActivity implements OnMapReadyCallback

{

    private GoogleMap mMap;
    private MapFragment mMapFragment;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = getIntent();

        mMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map, mMapFragment);
        fragmentTransaction.commit();
        mMapFragment.getMapAsync(this);
    }


    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng skola1 = new LatLng(59.377524, 17.934259);
        LatLng skola2 = new LatLng(59.374839, 17.966441);

        mMap.addMarker(new MarkerOptions().position(skola1).title("Grönkullaskolan"));
        mMap.addMarker(new MarkerOptions().position(skola2).title("Kulturama Grundsk. Sundbyberg"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(skola1));
        mMap.getUiSettings().isMapToolbarEnabled();
        mMap.getUiSettings().setZoomControlsEnabled(true);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            Toast.makeText(this, "Request permission.", Toast.LENGTH_SHORT).show();
        }


    }
}
