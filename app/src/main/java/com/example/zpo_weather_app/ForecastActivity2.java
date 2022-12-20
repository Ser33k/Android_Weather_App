package com.example.zpo_weather_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Cartesian;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.ValueDataEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;


public class ForecastActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast2);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Intent intent = getIntent();

        Forecast forecast  = (Forecast) intent.getSerializableExtra("EXTRA_FORECAST");

        TextView tv = (TextView)findViewById(R.id.textView);


        tv.setText(forecast.getCityAndCountry());

        List<DataEntry> cloudsData = new ArrayList<>();
        List<DataEntry> temperatureData = new ArrayList<>();

        for (int i = 0; i < forecast.getClouds().size(); i += 8){
            String date = forecast.getTemperatures().get(i).getDate().split(" ")[0].substring(8) + "/" + forecast.getTemperatures().get(i).getDate().split(" ")[0].substring(5,7);
            temperatureData.add(new ValueDataEntry(date, forecast.getTemperatures().get(i).getTemperature()));

//            String date = forecast.getClouds().get(i).getDate().split(" ")[0].substring(8) + "/" + forecast.getTemperatures().get(i).getDate().split(" ")[0].substring(5,7);
            cloudsData.add(new ValueDataEntry(date, forecast.getClouds().get(i).getClouds()));

            Log.e("TA321G", String.valueOf(forecast.getClouds().get(i).getClouds()));

        }

        AnyChartView anyChartView = findViewById(R.id.any_chart_view2);
        Cartesian cartesian = AnyChart.column();
        cartesian.column(cloudsData);
        cartesian.setBackground("#e1f5fe");
        cartesian.setTitle("Cloudiness");

        cartesian.getXAxis().setTitle("Date");
        cartesian.getYAxis().setTitle("Clouds [%]");

        cartesian.getYScale().setMinimum(0.);
        cartesian.getYScale().setMaximum(100.);

        anyChartView.setChart(cartesian);


        AnyChartView tempChartView = findViewById(R.id.any_chart_view);
        Cartesian tempCartesian = AnyChart.line();
        tempCartesian.line(temperatureData);
        tempCartesian.setTitle("Temperature");
        tempCartesian.getXAxis().setTitle("Date");
        tempCartesian.getYAxis().setTitle("Temperature [°C]");
        tempCartesian.setBackground("#e1f5fe");
        tempChartView.setChart(tempCartesian);



    }
}