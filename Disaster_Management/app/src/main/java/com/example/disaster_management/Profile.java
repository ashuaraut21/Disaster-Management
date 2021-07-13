package com.example.disaster_management;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

//import static com.example.disaster_management.Login.MyPREF;

public class Profile extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView Profile_Name , Profile_Contact , Profile_Email ,Profile_Id ;
    ImageView Profile_Pic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Profile_Name = (TextView)findViewById(R.id.profile_name);
        Profile_Contact = (TextView)findViewById(R.id.profile_contact);
        Profile_Email = (TextView)findViewById(R.id.profile_email);
        Profile_Id = (TextView)findViewById(R.id.profile_batch);
        Profile_Pic = (ImageView)findViewById(R.id.profile_pic);
        sharedPreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);



        String Name = sharedPreferences.getString("name" , "name");
        String Email = sharedPreferences.getString("email" , "email");
        String Contact = sharedPreferences.getString("contact" , "contact");
        String ID = sharedPreferences.getString("id" , "id");

        Profile_Name.setText("NAME :" + "\n\n" +Name);
        Profile_Email.setText("E-MAIL :" +"\n\n" +Email);
        Profile_Contact.setText("CONTACT :" + "\n\n" +Contact);
        Profile_Id.setText("ID :" + "\n\n" +ID);

//        Toast.makeText(Profile.this , Name + Email , Toast.LENGTH_LONG).show();

    }
}
