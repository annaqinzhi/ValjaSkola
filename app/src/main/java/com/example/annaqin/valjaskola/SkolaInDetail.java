package com.example.annaqin.valjaskola;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.annaqin.valjaskola.MainActivity.Extra_Adress;
import static com.example.annaqin.valjaskola.MainActivity.Extra_Gmeriv;
import static com.example.annaqin.valjaskola.MainActivity.Extra_Name;
import static com.example.annaqin.valjaskola.MainActivity.Extra_Tel;
import static com.example.annaqin.valjaskola.MainActivity.Extra_Type;
import static com.example.annaqin.valjaskola.MainActivity.Extra_Uppn;
import static com.example.annaqin.valjaskola.MainActivity.Extra_Utbil;

public class SkolaInDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skola_in_detail);

        Intent intent=getIntent();

        String name=intent.getStringExtra(Extra_Name);
        String type=intent.getStringExtra(Extra_Type);
        String gmeriv=intent.getStringExtra(Extra_Gmeriv);
        String adress=intent.getStringExtra(Extra_Adress);
        String tel=intent.getStringExtra(Extra_Tel);
        String utbil=intent.getStringExtra(Extra_Utbil);
        String uppn=intent.getStringExtra(Extra_Uppn);



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


    }
}
