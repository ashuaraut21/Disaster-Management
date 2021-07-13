package com.example.disaster_management;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
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

public class DamData extends AppCompatActivity {
    String[] data_id,data,date;
    AnyChartView anyChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dam_data);
        anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        try {

            DbParameter host=new DbParameter();
            String url=host.getHostpath();
            url=url+"damdata.php";
            getJSON(url);
        }catch (Exception e){
            Toast.makeText(DamData.this,""+e,Toast.LENGTH_LONG).show();
        }



    }





    //ADD FOLLOWING line in manifest android:usesCleartextTraffic="true"
    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {
            private ProgressDialog progress = null;

            @Override
            protected void onPreExecute() {
                progress = ProgressDialog.show(DamData.this, null, "Login...");
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
            Toast.makeText(DamData.this,"Please Enter Valid Username & Password",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(DamData.this,json,Toast.LENGTH_LONG).show();
            JSONArray jsonArray = new JSONArray(json);
            data = new String[jsonArray.length()];
            data_id = new String[jsonArray.length()];
            date = new String[jsonArray.length()];
            Cartesian cartesian = AnyChart.line();

            cartesian.animation(true);

            cartesian.padding(10d, 20d, 5d, 20d);

            cartesian.crosshair().enabled(true);
            cartesian.crosshair()
                    .yLabel(true)
                    // TODO ystroke
                    .yStroke((Stroke) null, null, null, (String) null, (String) null);

            cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

            cartesian.title("Trend of Sales of the Most Popular Products of ACME Corp.");

            cartesian.yAxis(0).title("Number of Bottles Sold (thousands)");
            cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);


            List<DataEntry> data_arry = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                data_id[i]=obj.getString("id");
                data[i]=obj.getString("gasdata");
                date[i]=obj.getString("days");
                //data_arry.add(new ValueDataEntry(date[i],Integer.parseInt(data[i]) ));
                data_arry.add(new CustomDataEntry(date[i], Integer.parseInt(data[i]), Integer.parseInt(data[i]), Integer.parseInt(data[i])));
            }


            Set set = Set.instantiate();
            set.data(data_arry);
            Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
            //Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
            //Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

            Line series1 = cartesian.line(series1Mapping);
            series1.name("GAS");
            series1.hovered().markers().enabled(true);
            series1.hovered().markers()
                    .type(MarkerType.CIRCLE)
                    .size(4d);
            series1.tooltip()
                    .position("right")
                    .anchor(Anchor.LEFT_CENTER)
                    .offsetX(5d)
                    .offsetY(5d);

//            Line series2 = cartesian.line(series2Mapping);
//            series2.name("Whiskey");
//            series2.hovered().markers().enabled(true);
//            series2.hovered().markers()
//                    .type(MarkerType.CIRCLE)
//                    .size(4d);
//            series2.tooltip()
//                    .position("right")
//                    .anchor(Anchor.LEFT_CENTER)
//                    .offsetX(5d)
//                    .offsetY(5d);
//
//            Line series3 = cartesian.line(series3Mapping);
//            series3.name("Tequila");
//            series3.hovered().markers().enabled(true);
//            series3.hovered().markers()
//                    .type(MarkerType.CIRCLE)
//                    .size(4d);
//            series3.tooltip()
//                    .position("right")
//                    .anchor(Anchor.LEFT_CENTER)
//                    .offsetX(5d)
//                    .offsetY(5d);

            cartesian.legend().enabled(true);
            cartesian.legend().fontSize(13d);
            cartesian.legend().padding(0d, 0d, 10d, 0d);

            anyChartView.setChart(cartesian);


        }




    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }
}
