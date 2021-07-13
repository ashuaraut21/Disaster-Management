package com.example.disaster_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SensorData extends AppCompatActivity {
    ImageView gassensor,firesensor,damsensor,temp,huma;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_data);
        gassensor=(ImageView)findViewById(R.id.gas_sensor);
        firesensor=(ImageView)findViewById(R.id.fire);
        damsensor=(ImageView)findViewById(R.id.dam);
        temp=(ImageView)findViewById(R.id.temp);
        huma=(ImageView)findViewById(R.id.huma);


        gassensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(SensorData.this, GasData.class);
                startActivity(i1);
            }
        });
        firesensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(SensorData.this, FireData.class);
                startActivity(i1);
            }
        });
        damsensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(SensorData.this, DamData.class);
                startActivity(i1);
            }
        });
        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(SensorData.this, tempAndHumi.class);
                startActivity(i1);
            }
        });
        huma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(SensorData.this, tempAndHumi.class);
                startActivity(i1);
            }
        });



    }
}
