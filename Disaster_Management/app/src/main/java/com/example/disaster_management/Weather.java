package com.example.disaster_management;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Weather extends AppCompatActivity {
    EditText city;
    Button search;
    TextView txt_wind, txt_temp, txt_humi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        city = (EditText) findViewById(R.id.city);
        search = (Button) findViewById(R.id.search);
        txt_humi=(TextView)findViewById(R.id.humi) ;
        txt_temp=(TextView)findViewById(R.id.temp) ;
        txt_wind=(TextView)findViewById(R.id.wind) ;
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    String url="http://api.openweathermap.org/data/2.5/weather?q="+city.getText().toString()+"&appid=";//API KEY REQUIRED
                    getJSON(url);
                }catch (Exception e){
                    Toast.makeText(Weather.this,""+e,Toast.LENGTH_LONG).show();
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
                progress = ProgressDialog.show(Weather.this, null, "Login...");
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
        //Toast.makeText(Weather.this, json, Toast.LENGTH_LONG).show();
        JSONObject reader = new JSONObject(json);
        JSONObject sys  = reader.getJSONObject("main");
        String temp = sys.getString("temp");
        String humi = sys.getString("humidity");
        JSONObject sys1 = reader.getJSONObject("wind");
        String wind = sys1.getString("speed");
        float temp_int= (float) (Float.parseFloat(temp)-273.15);
        txt_wind.setText(wind+" Km");
        txt_temp.setText(temp_int+" C");
        txt_humi.setText(humi+"%");
        Toast.makeText(Weather.this, temp, Toast.LENGTH_LONG).show();


        JSONArray jsonArray = new JSONArray(json);


        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(0);
            //data_id[i]=obj.getString("id");

        }

    }

}




