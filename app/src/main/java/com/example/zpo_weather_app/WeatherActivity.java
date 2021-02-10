package com.example.zpo_weather_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

/**
 * represents the WeatherActivity
 */
public class WeatherActivity extends AppCompatActivity {

    public static final Weather EXTRA_WEATHER = new Weather();

    /**
     * method is called only when the activity starts
     * gets the weather's object from main activity
     * and by getters the data are displayed
     * in the textViews
     * depending on the weather the background changes
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather2);
        Objects.requireNonNull(getSupportActionBar()).hide();


        Intent intent = getIntent();

        Weather weather = (Weather) intent.getSerializableExtra("EXTRA_WEATHER");

        TextView cityAndCountryTv = (TextView)findViewById(R.id.cityAndCountryTV);
        ImageView iconIv = (ImageView)findViewById(R.id.iconIV);
        TextView mainTv = (TextView)findViewById(R.id.mainTV);
//        TextView descriptionTv = (TextView)findViewById(R.id.descriptionTV);
        TextView pressureTv = (TextView)findViewById(R.id.pressureTV);
        TextView windTv = (TextView)findViewById(R.id.windTV);
        TextView humidityTv = (TextView)findViewById(R.id.humidityTV);

        TextView temperatureTv = (TextView)findViewById(R.id.temperatureTV);
        TextView feelsTemperatureTv = findViewById(R.id.feelsTemperatureTV);


        cityAndCountryTv.setText(weather.getCityAndCountry());
//        String iconUrl = "http://openweathermap.org/img/wn/"+weather.getIconNumber()+"@2x.png";
//        Picasso.with(this).load(iconUrl).into(iconIv);
        mainTv.setText(weather.getMain());
//        descriptionTv.setText(weather.getDescription());
        pressureTv.setText(weather.getPressure() + " hPa");
        windTv.setText(weather.getWind() + " m/s");
        humidityTv.setText(weather.getHumidity() + "%");

        temperatureTv.setText(weather.getTemperature()+"°C");
        feelsTemperatureTv.setText("Feels like " + weather.getFeelsTemperature()+"°C");


        ConstraintLayout constraintLayout = findViewById(R.id.weatherLayout);
        switch (weather.getMain().toLowerCase()) {
            case "clear":
                constraintLayout.setBackgroundResource(R.drawable.clearsky);
                iconIv.setImageResource(R.drawable.sun);

                break;
            case "few clouds":
            case "clouds":
            case "broken clouds":
                constraintLayout.setBackgroundResource(R.drawable.clouds);
                iconIv.setImageResource(R.drawable.cloudcomputing);

                break;
            case "drizzle":
            case "rain":
                constraintLayout.setBackgroundResource(R.drawable.showerrain);
                iconIv.setImageResource(R.drawable.rain);
                break;
            case "thunderstorm":
                constraintLayout.setBackgroundResource(R.drawable.thunderstorm);
                iconIv.setImageResource(R.drawable.storm);

                break;
            case "snow":
                constraintLayout.setBackgroundResource(R.drawable.snow2);
                iconIv.setImageResource(R.drawable.snow);

                break;
            case "mist":
            case "fog":
                constraintLayout.setBackgroundResource(R.drawable.mist);
                iconIv.setImageResource(R.drawable.foggy);

                break;


        }


    }
}