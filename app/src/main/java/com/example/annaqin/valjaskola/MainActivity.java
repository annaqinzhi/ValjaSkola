package com.example.annaqin.valjaskola;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback

{

    private GoogleMap mMap;
    private MapFragment mMapFragment;
    Intent intent;
    ArrayList<Skola> skolorList;
    String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        intent = getIntent();
        TAG="Firebase";

        mMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map, mMapFragment);
        fragmentTransaction.commit();
        mMapFragment.getMapAsync(this);

    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().isMapToolbarEnabled();
        mMap.getUiSettings().setZoomControlsEnabled(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference skolor = database.getReference("skolor");
        skolor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot!=null){

                    for (DataSnapshot childSnapshot:dataSnapshot.getChildren()){
                        skolorList=new ArrayList<>();

                        Skola skola=childSnapshot.getValue(Skola.class);
                        skolorList.add(skola);
                        double lat=skola.getLatitude();
                        double lng=skola.getLongitude();
                        LatLng skolaLatlng = new LatLng(lat, lng);
                        mMap.addMarker(new MarkerOptions().position(skolaLatlng).title(skola.getName()));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(skolaLatlng));
                        Log.d(TAG, skola.getName()+" has been added: " + skola.getLatitude()+"  "+skola.getLongitude());
                    }

                } else {
                    Toast.makeText(MainActivity.this, "This is no data in firebase!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            Toast.makeText(this, "Request permission.", Toast.LENGTH_SHORT).show();
        }

    }

}
