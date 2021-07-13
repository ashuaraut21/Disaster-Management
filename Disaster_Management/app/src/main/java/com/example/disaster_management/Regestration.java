package com.example.disaster_management;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Regestration extends AppCompatActivity {

    private Button button , Login;
    private EditText username,password,cnfpassword,phone_no,gmail;
    public String edt_username,edt_phone,edt_gmail,edt_cnfpassword,edt_password;
    // public int edt_cnfpassword,edt_password;

    private final String URL ="https://arprojects.tech/Aniket/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestration);

        button=(Button)findViewById(R.id.button);
        username=(EditText)findViewById(R.id.username);
        gmail=(EditText)findViewById(R.id.edtmail);
        phone_no=(EditText)findViewById(R.id.edtphone);
        password=(EditText)findViewById(R.id.password);
        cnfpassword=(EditText)findViewById(R.id.re_password);
        Login=(Button)findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Regestration.this, Login.class);
                startActivity(intent);
            }
        });
    }

    public void register(){
        initialize();
        if(!validate()){
            Toast.makeText(this,"registration has faild!",Toast.LENGTH_SHORT).show();
        }else {
            registersucess();
        }
    }
    public void registersucess(){
        process();
    }



    public boolean validate(){
        boolean valid=true;
        if(edt_username.isEmpty()||edt_username.length()>32){
            username.setError("please enter valid username");
            return false;
        }
        if(edt_gmail.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(edt_gmail).matches()){
            gmail.setError("please enter valid gmail ");
            return false;

        }

        if(edt_phone.isEmpty()|| edt_phone.length()>12){
            phone_no.setError("please enter valid phone");
            return false;

        } if(!edt_password.equals(edt_cnfpassword)){
            cnfpassword.setError("password dosn't match");

            return false;
        }

        return true;

    }
    public void initialize(){

        edt_username=username.getText().toString().trim();
        edt_gmail=gmail.getText().toString().trim();
        edt_phone=phone_no.getText().toString().trim();
        edt_password=password.getText().toString().trim();
        edt_cnfpassword= cnfpassword.getText().toString().trim();
    }


    private void process() {

        String n1 = username.getText().toString();
        String n2 = gmail.getText().toString();
        String n3 = phone_no.getText().toString();
        String n4 = password.getText().toString();
        String n5 = cnfpassword.getText().toString();
        String qrystring =URL+"?name="+n1+"&email="+n2+"&contact="+n3+"&pass="+n4+"&cpass="+n5;


        class dbclass extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String data) {
                username.setText("");
                phone_no.setText("");
                gmail.setText("");
                password.setText("");
                cnfpassword.setText("");

                //tv.setText(data);
                Toast.makeText(getApplicationContext() , data , Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... param) {

                try {
                    java.net.URL url = new URL(param[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    return br.readLine();
                } catch (Exception ex) {
                    return ex.getMessage();

                }

            }
        }

        dbclass obj = new dbclass();
        obj.execute(qrystring);
    }

}
