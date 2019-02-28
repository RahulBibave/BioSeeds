package kumarworld.rahul.kumarbioseeds.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kumarworld.rahul.kumarbioseeds.R;
import kumarworld.rahul.kumarbioseeds.adapter.AdapterForeCast;
import kumarworld.rahul.kumarbioseeds.model.Forecast;

public class Activity_WetherReport extends AppCompatActivity {

    private RecyclerView mRecyclerViewForeCast;
    private ArrayList<Forecast> mForecastArrayList;
    private AdapterForeCast mAdapterForeCast;
    private ProgressDialog progressDialog;


    private RelativeLayout widgetRoot;
    private LinearLayout linearLayout;
    private TextView txtDate;
    private TextView widgetTemperature;
    private ImageButton widgetButtonRefresh;
    private TextView widgetDescription;
    private TextView widgetCity;
    private TextView widgetWind;
    private TextView widgetPressure;
    private TextView widgetHumidity;
    private TextView widgetSunrise;
    private TextView widgetSunset;
    private TextView widgetLastUpdate;
    private ImageView widgetIcon;
    private ImageView img_arrowback;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        findViews();
        mRecyclerViewForeCast = findViewById(R.id.recyclerForecast);
        mRecyclerViewForeCast.setLayoutManager(new LinearLayoutManager(this));
        getWeatherReport();
        img_arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void findViews() {
        widgetRoot = findViewById(R.id.widgetRoot);
        linearLayout = findViewById(R.id.linearLayout);
        txtDate = findViewById(R.id.txtDate);
        widgetTemperature = findViewById(R.id.widgetTemperature);
        widgetButtonRefresh = findViewById(R.id.widgetButtonRefresh);
        widgetDescription = findViewById(R.id.widgetDescription);
        widgetCity = findViewById(R.id.widgetCity);
        widgetWind = findViewById(R.id.widgetWind);
        widgetPressure = findViewById(R.id.widgetPressure);
        widgetHumidity = findViewById(R.id.widgetHumidity);
        widgetSunrise = findViewById(R.id.widgetSunrise);
        widgetSunset = findViewById(R.id.widgetSunset);
        widgetLastUpdate = findViewById(R.id.widgetLastUpdate);
        widgetIcon = findViewById(R.id.widgetIcon);
        img_arrowback=findViewById(R.id.img_arrowback);


    }


    public void getWeatherReport() {
        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22Pune%2C%20MH%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        RequestQueue requestQueue = Volley.newRequestQueue(Activity_WetherReport.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int msg_Code;
                        progressDialog.dismiss();
                        mForecastArrayList = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonArray = jsonObject.getJSONObject("query");
                            JSONObject jsonObject1 = jsonArray.getJSONObject("results");
                            JSONObject jsonObject2 = jsonObject1.getJSONObject("channel");
                            JSONObject jsonObject3 = jsonObject2.getJSONObject("item");


                            /*Wind*/
                            JSONObject jsonWind = jsonObject2.getJSONObject("wind");
                            String windSpeed = jsonWind.getString("speed");
                            widgetWind.setText("Wind : " + windSpeed + " km/h NNE");


                            /*Sunset and SunRise*/
                            JSONObject jsonastronomy = jsonObject2.getJSONObject("astronomy");
                            String sunrise = jsonastronomy.getString("sunrise");
                            String sunset = jsonastronomy.getString("sunset");
                            widgetSunrise.setText("Sunrise : " + sunrise);
                            widgetSunset.setText("Sunset : " + sunset);


                            /*Humadity,pressure*/
                            JSONObject jsonatmosphere = jsonObject2.getJSONObject("atmosphere");
                            String humidity = jsonatmosphere.getString("humidity");
                            String pressure = jsonatmosphere.getString("pressure");
                            widgetHumidity.setText("Humidity : " + humidity + " %");
                            widgetPressure.setText("Pressure : " + pressure + " mBar");

                            String city = jsonObject2.getString("description");
                            String[] parts = city.split("for ");
                            widgetCity.setText(parts[1]);

                            String tital = jsonObject3.getString("title");
                            txtDate.setText(tital);

                            JSONObject jsonObject4 = jsonObject3.getJSONObject("condition");
                            String condition = jsonObject4.getString("text");
                            String temp = jsonObject4.getString("temp");
                            int temp1 = Integer.parseInt(temp);
                            temp1 = temp1 - 32;
                            temp1 = temp1 * 5;
                            temp1 = temp1 / 9;
                            widgetTemperature.setText(temp1 + " °C");
                            widgetDescription.setText(condition);


                            /*Forecast*/
                            JSONArray json_forecast = jsonObject3.getJSONArray("forecast");
                            for (int i = 0; i < json_forecast.length(); i++) {
                                JSONObject jsonForeCast = json_forecast.getJSONObject(i);

                                String Code, Date, Day, High, Low, Text;
                                Code = jsonForeCast.getString("code");
                                Date = jsonForeCast.getString("date");
                                Day = jsonForeCast.getString("day");
                                High = jsonForeCast.getString("high");
                                Low = jsonForeCast.getString("low");
                                Text = jsonForeCast.getString("text");
                                Forecast forecast = new Forecast(Code, Date, Day, High, Low, Text);
                                mForecastArrayList.add(forecast);
                            }

                            mAdapterForeCast = new AdapterForeCast(mForecastArrayList, Activity_WetherReport.this);
                            mRecyclerViewForeCast.setAdapter(mAdapterForeCast);
                            mAdapterForeCast.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                });
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(Activity_WetherReport.this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();

    }


}


