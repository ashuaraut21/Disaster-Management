package com.example.disaster_management;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import static com.example.disaster_management.Add_Number.MyPREFRENCES;
import static com.example.disaster_management.Login.MyPREFRENCES;

public class View_Number extends AppCompatActivity {

    Button VIEW;
    TextView NO1 ,NO2 , NO3 , NO4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__number);

        NO1 = (TextView)findViewById(R.id.no_1);
        NO2 = (TextView)findViewById(R.id.no_2);
        NO3 = (TextView)findViewById(R.id.no_3);
        NO4 = (TextView)findViewById(R.id.no_4);
        VIEW = (Button)findViewById(R.id.btn_view);

        VIEW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(MyPREFRENCES, Context.MODE_PRIVATE);

                String n1 = sharedPreferences.getString("noKey1" , "n1");
                String n2 = sharedPreferences.getString("noKey2" , "n2");
                String n3 = sharedPreferences.getString("noKey3" , "n3");
                String n4 = sharedPreferences.getString("noKey4" , "n4");


                NO1.setText("Number1 :" +n1);
                NO2.setText("Number2 :" +n2);
                NO3.setText("Number3 :" +n3);
                NO4.setText("Number4 :" +n4);

                Toast.makeText(View_Number.this , n1 , Toast.LENGTH_LONG).show();

            }
        });
    }
}
