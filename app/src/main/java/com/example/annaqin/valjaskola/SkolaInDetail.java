package com.example.annaqin.valjaskola;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.annaqin.valjaskola.MainActivity.Extra_Adress;
import static com.example.annaqin.valjaskola.MainActivity.Extra_Gmeriv;
import static com.example.annaqin.valjaskola.MainActivity.Extra_Lat;
import static com.example.annaqin.valjaskola.MainActivity.Extra_Lng;
import static com.example.annaqin.valjaskola.MainActivity.Extra_Name;
import static com.example.annaqin.valjaskola.MainActivity.Extra_Tel;
import static com.example.annaqin.valjaskola.MainActivity.Extra_Type;
import static com.example.annaqin.valjaskola.MainActivity.Extra_Uppn;
import static com.example.annaqin.valjaskola.MainActivity.Extra_Utbil;

public class SkolaInDetail extends AppCompatActivity {

    private FragmentManager manager;
    private GoogleMap mMap;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skola_in_detail);

        Intent intent=getIntent();
        manager = getFragmentManager();

        final String name=intent.getStringExtra(Extra_Name);
        String type=intent.getStringExtra(Extra_Type);
        String gmeriv=intent.getStringExtra(Extra_Gmeriv);
        final String adress=intent.getStringExtra(Extra_Adress);
        String tel=intent.getStringExtra(Extra_Tel);
        String utbil=intent.getStringExtra(Extra_Utbil);
        String uppn=intent.getStringExtra(Extra_Uppn);
        final String lat=intent.getStringExtra(Extra_Lat);
        final String lng=intent.getStringExtra(Extra_Lng);

        TextView textViewName=(TextView)findViewById(R.id.textView_SkolaName);
        TextView textViewType=(TextView)findViewById(R.id.textView_SkolaType);
        TextView textViewGmeriv=(TextView)findViewById(R.id.textView_SkolaGmeriv);
        TextView textViewAdress=(TextView)findViewById(R.id.textView_SkolaAdress);
        TextView textViewTel=(TextView)findViewById(R.id.textView_SkolaTel);
        TextView textViewUtbil=(TextView)findViewById(R.id.textView_Utbildning);
        TextView textViewUppn=(TextView)findViewById(R.id.textView_Uppn);

        textViewName.setText(name);
        textViewType.setText(type);
        textViewGmeriv.setText(gmeriv);
        textViewAdress.setText(adress);
        textViewTel.setText(tel);
        textViewUtbil.setText(utbil);
        textViewUppn.setText(uppn);

        MapFragment mapFragment = new MapFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.itemsMap, mapFragment, "mapFragment");
        transaction.commit();

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.getUiSettings().isMapToolbarEnabled();
                mMap.getUiSettings().setZoomControlsEnabled(true);

                // For showing a move to my location button
                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                } else {
                    Toast.makeText(getApplicationContext(), "Request permission.", Toast.LENGTH_SHORT).show();
                }

                // For dropping markers at the points on the Map
                double sLat = Double.parseDouble(lat);
                double sLng = Double.parseDouble(lng);

                LatLng sLocation = new LatLng(sLat, sLng);
                mMap.addMarker(new MarkerOptions().position(sLocation).title(name).snippet(adress));

                // For zooming automatically to the location of the marker
                locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sLocation).zoom(10).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

    }

}
