package com.example.annaqin.valjaskola;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchActivity extends AppCompatActivity {
    Intent intent;
    public static final String TAG = "Firebase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpage);
        intent = getIntent();
        getPositions();

    }

    public void getPositions() {

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference skolor = mDatabase.getReference("skola1");

        skolor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Skola skola = child.getValue(Skola.class);
                    if (skola.latitude == 59.377524){
                        Log.d(TAG, "59.377524 finns ");
                    Log.d(TAG, "latitude for skola1: " + skola.latitude );}
                    else {Log.d(TAG, "59.377524 finns inte ");}

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
