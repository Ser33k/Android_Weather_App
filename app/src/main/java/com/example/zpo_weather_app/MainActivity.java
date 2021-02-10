package com.example.zpo_weather_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * represents the MainActivity
 */
public class MainActivity extends AppCompatActivity {

    TextView tv;

    /**
     * method is called only when the activity starts
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        tv = (TextView) findViewById(R.id.textView4);
    }

    /**
     * the method gets weather data from https://openweathermap.org/
     * creates the instance of Weather class and puts it into the
     * WeatherActivity where all the data is displayed
     * @param cityName city that we want to get weather data
     */

    public void getData(String cityName){
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+cityName+"&units=metric&appid=b4d35388fa6838bcdd13de996aa27b00";

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            JsonParser parser = new JsonParser();
            JsonObject json = (JsonObject) parser.parse(response);

            String cityAndCountry = json.get("name").getAsString() + ", " + json.get("sys").getAsJsonObject().get("country").getAsString();
            String iconNumber = json.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();


            String main = json.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString();
            String description = json.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString();
            String pressure = json.get("main").getAsJsonObject().get("pressure").getAsString();
            String wind = json.get("wind").getAsJsonObject().get("speed").getAsString();
            String humidity = json.get("main").getAsJsonObject().get("humidity").getAsString();
            int temperature = (int) Math.round(json.get("main").getAsJsonObject().get("temp").getAsDouble());
            int feelsTemperature = (int) Math.round(json.get("main").getAsJsonObject().get("feels_like").getAsDouble());

            Weather weather = new Weather(cityAndCountry,iconNumber, main, description, pressure, wind, humidity, temperature, feelsTemperature);

            Intent intent = new Intent(this, WeatherActivity.class);
            intent.putExtra("EXTRA_WEATHER", weather);

            startActivity(intent);


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Wrong city name!",
                        Toast.LENGTH_LONG).show();
            }
        }
        );

        queue.add(stringRequest);
    }

    /**
     * a method that uses Geocoder class to get the name of city
     * @param lat latitude of the city we want to search
     * @param lon longitude of the city we want to search
     * @return the string of city name
     */
    private String hereLocation(double lat, double lon){
        String cityName = "";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(lat, lon, 10);
            if (addresses.size() > 0){
                for (Address adr : addresses){
                    if (adr.getLocality() != null && adr.getLocality().length() > 0){
                        cityName = adr.getLocality();
                        break;
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return cityName;
    }


    /**
     * the method is called when user clicks
     * the "Show weather for your location" button
     * gets the city name by hereLocation method
     * and then calls the getData method
     * but if the city is not found it creates the toast
     */
    public void onLocationClick() {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1000);

            }

            else {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                try {
                    String city = hereLocation(location.getLatitude(), location.getLongitude());

                    getData(city);
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Not found", Toast.LENGTH_SHORT).show();
                }

            }
    }

    /**
     * the method is called when user clicks
     * the "Show weather" button
     * gets the city name from the EditText
     * and then gets data for this city
     */
    public void showWeather() {
        EditText cityInput = (EditText)findViewById(R.id.cityInput);
        String cityName = cityInput.getText().toString();

        getData(cityName);
    }

}