package com.example.disaster_management;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity {

    EditText Subject , MsgBody , Name , MobileNo;
    Button Send;

    String n1 , name , msg , sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        Subject = (EditText)findViewById(R.id.msg_subject);
        MsgBody = (EditText)findViewById(R.id.msg_body);
        Name = (EditText)findViewById(R.id.sender_name);
        MobileNo = (EditText)findViewById(R.id.sender_contact);
        Send = (Button)findViewById(R.id.btn_send);

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n1=MobileNo.getText().toString().trim();
                name = Name.getText().toString().trim();
                msg = MsgBody.getText().toString().trim();
                sub = Subject.getText().toString().trim();

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(n1, null, "Sender_Name :"+""+name+"\n"+"Sender_Mob :"+n1+"\n"+"Subject :"+""+sub+"\n"+"Message :"+""+msg , null, null);
                Toast.makeText(getApplicationContext(), "Message Sent Successfully", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
