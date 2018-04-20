package com.example.annaqin.valjaskola;


import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static android.content.ContentValues.TAG;

public class MainActivity extends FragmentActivity implements ListAdapter.OnItemClickListener {
    public static final String Extra_Name="SkolaName";
    public static final String Extra_Type="SkolaType";
    public static final String Extra_Gmeriv="SkolaGmeriv";
    public static final String Extra_Adress="SkolaAdress";
    public static final String Extra_Tel="SkolaTel";
    public static final String Extra_Utbil="SkolaUtbil";
    public static final String Extra_Uppn="SkolaUppn";
    public static final String Extra_Lat="SkolaLat";
    public static final String Extra_Lng="SkolaLng";
    Intent intent;


    private RecyclerView mRecyclerView;
    private ListAdapter listAdapter;
    private DatabaseReference mDataReference;
    private Spinner spinnerKom;
    private Spinner spinnerLan;
    private String seletedItem1;
    private String seletedItem2;
    private TextView textViewVisa;
    private int count;
    private TextView textViewSorting;
    private ArrayAdapter<String> komAdapter;
    private TextView textViewToMap;
    private FragmentManager manager;
    private GoogleMap mMap;
    private LocationManager locationManager;

    private List<Skola>listskolor;
    private ArrayList<String> listKom;

    private boolean ascending = true;
    private boolean toMap = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getFragmentManager();
        intent = getIntent();

        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager=new RecyclerView.LayoutManager() {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return null;
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

        spinnerLan = (Spinner) findViewById(R.id.spinner_lan);
        spinnerKom = (Spinner) findViewById(R.id.spinner_kommun);

        listskolor =new ArrayList<Skola>();
        listKom = new ArrayList<String>();
        //listKom.add(getString(R.string.spinner_kommunAlla));


        // if choose län:
        addListenerOnSpinnerLan();

        //if choose kommun:
        addListenerOnSpinnerKom();

        //sorting by Genomsnittligt merivärde
        textViewSorting =(TextView)findViewById(R.id.textView_sorting);
        textViewSorting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByGmeriv(ascending);
                ascending=!ascending;

            }
        });

        // Switch to Map or List

        textViewToMap=(TextView)findViewById(R.id.textView_toMap);
        textViewToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMapFragment(toMap);
                toMap=!toMap;
            }
        });

    }


    // if onclick Spinner_lan to choose Län: 1. get data from firebase and show them; 2. create the list for spinner_kom:
    public void addListenerOnSpinnerLan(){
        ArrayAdapter<CharSequence> lanAdapter =
                ArrayAdapter.createFromResource(getApplicationContext(), R.array.spinner_lan, android.R.layout.simple_spinner_item);
        lanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerLan.setAdapter(lanAdapter);
        spinnerLan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                seletedItem1= parent.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, seletedItem1, Toast.LENGTH_SHORT).show();

                onClickSpinnerLanGetData();

                if(!toMap){
                    removeMap();
                    getMap();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    // if onclick Spinner_kom to choose kommun:  get data from firebase and show them:
    public void addListenerOnSpinnerKom(){

        spinnerKom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                seletedItem2= parent.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, seletedItem2, Toast.LENGTH_SHORT).show();

                onClickSpinnerKomGetData();

                if(!toMap){
                    removeMap();
                    getMap();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    //Method to get data from firebase and show them
    public void onClickSpinnerLanGetData() {
        listskolor.clear();
        listKom.clear();
        count=0;

        mRecyclerView.removeAllViews();
        mDataReference = FirebaseDatabase.getInstance().getReference("skolor");
        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot skolaSnapshot : dataSnapshot.getChildren()) {
                    Skola skola = skolaSnapshot.getValue(Skola.class);
                    String komInDBS=(String) skolaSnapshot.child("kummun").getValue();
                    String lanInDBS=(String) skolaSnapshot.child("lan").getValue();

                    if(lanInDBS.contains(seletedItem1)){
                        listskolor.add(skola);
                        listKom.add(komInDBS);
                        count++;
                    } else if (seletedItem1.contains(getString(R.string.spinner_lanAlla))){
                        listskolor.add(skola);
                        listKom.add(komInDBS);
                        count++;
                    }
                }

                Set<String> uniqueKom = new TreeSet<String>(listKom);
                listKom = new ArrayList<>(uniqueKom);
                for (String str : listKom) {
                    Log.d(TAG, "onClickSpinnerLanGetData: spinnerKom item :" + str);
                }
                listKom.add(0, getString(R.string.spinner_kommunAlla));
                Log.d(TAG, "onClickSpinnerLanGetData: spinnerKom size: " + listKom.size());

                komAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listKom);
                komAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerKom.setAdapter(komAdapter);

                setListAdapter();

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    //Method to get data from firebase and show them
    public void onClickSpinnerKomGetData() {
        listskolor.clear();
        count=0;

        mRecyclerView.removeAllViews();
        mDataReference = FirebaseDatabase.getInstance().getReference("skolor");
        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot skolaSnapshot : dataSnapshot.getChildren()) {
                    Skola skola = skolaSnapshot.getValue(Skola.class);
                    String komInDBS=(String) skolaSnapshot.child("kummun").getValue();
                    String lanInDBS=(String) skolaSnapshot.child("lan").getValue();

                    if(seletedItem1.contains(getString(R.string.spinner_lanAlla))&& seletedItem2.contains(getString(R.string.spinner_kommunAlla))){
                        listskolor.add(skola);
                        count++;
                    } else if (lanInDBS.contains(seletedItem1)&& seletedItem2.contains(getString(R.string.spinner_kommunAlla))){
                        listskolor.add(skola);
                        count++;
                    } else if (komInDBS.contains(seletedItem2)){
                        listskolor.add(skola);
                        count++;
                    }

                }

                setListAdapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    //Set list of schools into Adapter
    public void setListAdapter() {

        listAdapter = new ListAdapter(listskolor);
        mRecyclerView.setAdapter(listAdapter);
        listAdapter.setOnItemClickListener(MainActivity.this);

        textViewVisa=(TextView)findViewById(R.id.textView_visa);
        textViewVisa.setText(count+" "+getString(R.string.traffar));


    }

    //SORT ARRAY ASCENDING AND DESCENDING
    public void sortByGmeriv(boolean asc){

        if (asc)
        {
            Collections.sort(listskolor);
        }
        else
        {
            Collections.reverse(listskolor);
        }

        setListAdapter();
    }

    @Override
    public void onItemClick(int position) {
        Intent detailintent=new Intent(this, SkolaInDetail.class);
        Skola clickedSkola=listskolor.get(position);

        detailintent.putExtra(Extra_Name, clickedSkola.getName());
        detailintent.putExtra(Extra_Type,clickedSkola.getType());
        detailintent.putExtra(Extra_Gmeriv,Double.toString(clickedSkola.getGmeriv()));
        detailintent.putExtra(Extra_Adress,clickedSkola.getAdress());
        detailintent.putExtra(Extra_Tel,clickedSkola.getTel());
        detailintent.putExtra(Extra_Utbil,clickedSkola.getUtbil());
        detailintent.putExtra(Extra_Uppn,clickedSkola.getUppn());
        detailintent.putExtra(Extra_Lat,Double.toString(clickedSkola.getLatitude()));
        detailintent.putExtra(Extra_Lng,Double.toString(clickedSkola.getLongitude()));

        startActivity(detailintent);

    }

    public void addMapFragment(boolean toMap){

        if(toMap){
            getMap();
            textViewToMap.setText(R.string.textView_toList);
       } else {
            removeMap();
            textViewToMap.setText(R.string.textView_toMap);
           }
    }

    public void getMap(){

        MapFragment mapFragment = new MapFragment();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, mapFragment, "mapFragment");
        transaction.commit();

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.getUiSettings().isMapToolbarEnabled();
                mMap.getUiSettings().setZoomControlsEnabled(true);

                // For showing a move to my location button
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Request permission.", Toast.LENGTH_LONG).show();
                } else {
                    mMap.setMyLocationEnabled(true);
                    // For dropping markers at the points on the Map
                    for (Skola skola : listskolor) {
                        LatLng sLocation = new LatLng(skola.getLatitude(), skola.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(sLocation).title(skola.getName()).snippet(skola.getAdress()));

                    }

                    // For zooming automatically to the location of the marker
                    locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
                    getMyLocation();
                }


            }

        });

    }

    public void removeMap(){

        MapFragment mapFragment =  (MapFragment) manager.findFragmentByTag("mapFragment");

        if (mapFragment != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(mapFragment);
            transaction.commit();
        } else {
            Toast.makeText(getApplicationContext(), "MapFragment not found", Toast.LENGTH_SHORT).show();
        }
    }

    public void getMyLocation(){

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            Location myLocation=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            LatLng mLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            CameraPosition cameraPosition = new CameraPosition.Builder().target(mLocation).zoom(10).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        } else {
            Toast.makeText(getApplicationContext(), "Request permission.", Toast.LENGTH_SHORT).show();
        }
    }


}





// Good altanative coding for 2 spinners:
   /* @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        seletedItem =parent.getItemAtPosition(position).toString();
        getDataFirebase();
        switch (parent.getId() ) {

            case R.id.spinner_lan :
                // if län
                addItemsOnSpinnerKom();

                break;

            case R.id.spinner_kommun :
                // if kommun

                getDataFirebase();

                break;
        }

        Toast.makeText(this, seletedItem,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/
