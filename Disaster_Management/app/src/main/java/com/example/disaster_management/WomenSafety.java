package com.example.disaster_management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class WomenSafety extends AppCompatActivity {

    CardView ADD_No , VIEW_No , Women_Safety;

    String NO1 , NO2 , NO3 , NO4;

    String message = "I Need Help . I am in danger . My location is ";

    //Location

    FusedLocationProviderClient fusedLocationProviderClient;

    String latitude , longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_women_safety);

        ADD_No = (CardView)findViewById(R.id.add_number);
        VIEW_No = (CardView)findViewById(R.id.view_number);
        Women_Safety = (CardView)findViewById(R.id.women_safety);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
                WomenSafety.this
        );

        ADD_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WomenSafety.this , Add_Number.class);
                startActivity(intent);
            }
        });

        VIEW_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WomenSafety.this , View_Number.class);
                startActivity(intent);
            }
        });

        Women_Safety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(WomenSafety.this , Emergency.class);
                startActivity(i1);
            }
        });

    }
}
