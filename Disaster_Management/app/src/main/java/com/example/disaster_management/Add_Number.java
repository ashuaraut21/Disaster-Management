package com.example.disaster_management;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_Number extends AppCompatActivity {

    EditText noOne , noTwo , noThree , noFour;
    Button ADD;


    //sharedPrefrences
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Number1 = "noKey1";
    public static final String Number2 = "noKey2";
    public static final String Number3 = "noKey3";
    public static final String Number4 = "noKey4";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__number);

        noOne = (EditText)findViewById(R.id.number_one);
        noTwo = (EditText)findViewById(R.id.number_two);
        noThree = (EditText)findViewById(R.id.number_three);
        noFour = (EditText)findViewById(R.id.number_four);
        ADD = (Button) findViewById(R.id.btn_add);

        //sharedPrefrences
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //OnclickListener

        ADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n1  = noOne.getText().toString();
                String n2  = noTwo.getText().toString();
                String n3  = noThree.getText().toString();
                String n4  = noFour.getText().toString();

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(Number1, n1);
                editor.putString(Number2, n2);
                editor.putString(Number3, n3);
                editor.putString(Number4, n4);
                editor.apply();
                editor.commit();
                editor.clear();
//                Intent i1 = new Intent(Add_Number.this , View_Number.class);
//                startActivity(i1);
                Toast.makeText(Add_Number.this,"successfully Added", Toast.LENGTH_LONG).show();
            }
        });


    }
}
