package com.example.disaster_management;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Login extends AppCompatActivity {

    TextView textView3;
    Button LOGIN;
    EditText username,password;
    String Password ;
    String name,contact,email,flat_no,id;
    SharedPreferences sharedpreferences;

    String[] EMAIL;
    String[] PASSWORD;
    String[] NAME;
    String[] CONTACT;

    private static final  String URL = "https://arprojects.tech/Aniket/userLogin.php";

    public ArrayList<String> holder = new ArrayList<>();

    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    final String MobilePattern = "[0-9]{10}";

    //sharedPrefrences

    public static final String MyPREFRENCES = "MyPrefs";
    public static final String Password1 = "passKey";
    public static final String Email1 = "emailKey";

    SharedPreferences SharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView3=(TextView)findViewById(R.id.textView3);
        LOGIN=(Button)findViewById(R.id.btn_login);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);

        //------------------------
        sharedpreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);


        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Regestration.class);
                startActivity(intent);
            }
        });

        LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_txt=username.getText().toString();
                String pass_txt=password.getText().toString();

                if(username.getText().length()>0){
                    if(password.getText().length()>0){
                        try {

                            DbParameter host=new DbParameter();
                            String url=host.getHostpath();
                            url=url+"OwnerLogin.php?"
                                    + "name="+ URLEncoder.encode(user_txt)
                                    + "&pass="+ URLEncoder.encode(pass_txt);
                            getJSON(url);
                        }catch (Exception e){
                            Toast.makeText(Login.this,""+e,Toast.LENGTH_LONG).show();
                        }


                    }else {
                        password.setError("Please Enter Valid Password");
                        password.requestFocus(20);
                    }


                }else{
                    username.setError("Please Enter Valid Username");
                    username.requestFocus(20);
                }






            }
        });


    }


    //ADD FOLLOWING line in manifest android:usesCleartextTraffic="true"
    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {
            private ProgressDialog progress = null;

            @Override
            protected void onPreExecute() {
                progress = ProgressDialog.show(Login.this, null, "Login...");
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progress.dismiss();
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {

        if(json.contentEquals("FAIL")){
            Toast.makeText(Login.this,"Please Enter Valid Username & Password",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(Login.this,json,Toast.LENGTH_LONG).show();
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                name=obj.getString("name");
                contact=obj.getString("contact");

                email=obj.getString("email");
                id =obj.getString("id");

            }

            SharedPreferences.Editor edit= sharedpreferences.edit();
            edit.putString("name",name);
            edit.putString("contact",contact);

            edit.putString("email", email);
            edit.putString("id", id);
            edit.commit();

            //Toast.makeText(Login.this,flat_no,Toast.LENGTH_LONG).show();
            Intent i1=new Intent(Login.this,HomeActivity.class);
            startActivity(i1);
            finish();
        }




    }

}
