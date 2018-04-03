package com.example.annaqin.valjaskola;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HemActivity extends AppCompatActivity {

    Intent intent1;
    Intent intent2;
    ImageButton grundskola;
    ImageButton gymnasieskola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hem);

        grundskola=(ImageButton)findViewById(R.id.grundskola);
        grundskola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent1= new Intent(HemActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });

        gymnasieskola=(ImageButton)findViewById(R.id.gymnasieskola);
        gymnasieskola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent2= new Intent(HemActivity.this, SearchActivity.class);
                startActivity(intent2);
            }
        });

    }
}
