package com.example.maincities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;


import com.android.volley.RequestQueue;


import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView tiempo;//Aqui se almacena el tiempo
    private TextView t_descrip;//Aqui se almacena descripccion del tiempo
    private TextView temperatura;
    String icon, main_tiempo, description, temp_c;
    private RequestQueue mQueue;//
    DecimalFormat df = new DecimalFormat("#.00");
    Button GoNew, GoMan, GoLon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoLon = (Button) findViewById(R.id.GoLon);
        GoLon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, London.class);
                startActivity(i);
            }
        });

        GoNew = (Button) findViewById(R.id.GoNew);
        GoNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NewYork.class);
                startActivity(i);

            }
        });

        GoMan = (Button) findViewById(R.id.GoMan);
        GoMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Managua.class);
                startActivity(i);

            }

        });

    }
}


