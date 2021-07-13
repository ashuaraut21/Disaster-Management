package com.example.disaster_management;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

//import static com.example.disaster_management.Add_Number.MyPREFRENCES;
import static com.example.disaster_management.Login.MyPREFRENCES;

public class Emergency extends AppCompatActivity {

    TextView LONGITUDE , LATITUDE;

    String Latitude ,Longitude;

    FusedLocationProviderClient fusedLocationProviderClient;

    String message = "I Need Help . I am in danger . My location is ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        LONGITUDE =(TextView)findViewById(R.id.longitude);
        LATITUDE = (TextView)findViewById(R.id.latitude);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
                Emergency.this
        );

        current_location();
    }
    public  void current_location(){
        if (ActivityCompat.checkSelfPermission(Emergency.this , Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(Emergency.this , Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            getCurrentLocation();
        }else{
            ActivityCompat.requestPermissions(Emergency.this,
                    new  String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==100 && grantResults.length > 0 && (grantResults[0] + grantResults[1]
                == PackageManager.PERMISSION_GRANTED)){
            getCurrentLocation();
        }else{
            Toast.makeText(getApplicationContext() , "Permission Denied" , Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE
        );

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location!=null){
                        LATITUDE.setText("Latitude :"+String.valueOf(location.getLatitude()));
                        LONGITUDE.setText("Longitude :"+String.valueOf(location.getLongitude()));

                        sendSMS();

                    }else {
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback(){
                            public void onLocationResult(LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                LATITUDE.setText("Latitude :"+String.valueOf(location1.getLatitude()));
                                LONGITUDE.setText("Longitude :"+String.valueOf(location1.getLongitude()));

                                sendSMS();
                            }
                        };
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                                locationCallback , Looper.myLooper());
                    }
                }
            });
        }else{
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    public  void sendSMS(){

        Latitude = LATITUDE.getText().toString();
        Longitude = LONGITUDE.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFRENCES, Context.MODE_PRIVATE);

        String n1 = sharedPreferences.getString("noKey1" , "n1");
        String n2 = sharedPreferences.getString("noKey2" , "n2");
        String n3 = sharedPreferences.getString("noKey3" , "n3");
        String n4 = sharedPreferences.getString("noKey4" , "n4");


        Toast.makeText(Emergency.this , n1 , Toast.LENGTH_LONG).show();

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(n1, null, message+":"+"latitude :"+Latitude+""+"longitude :"+Longitude, null, null);
        smsManager.sendTextMessage(n2, null, message+":"+"latitude :"+Latitude+""+"longitude :"+Longitude, null, null);
        smsManager.sendTextMessage(n3, null, message+":"+"latitude :"+Latitude+""+"longitude :"+Longitude, null, null);
        smsManager.sendTextMessage(n4, null, message+":"+"latitude :"+Latitude+""+"longitude :"+Longitude, null, null);
        Toast.makeText(getApplicationContext(), "Message Sent Successfully", Toast.LENGTH_LONG).show();
    }

}
