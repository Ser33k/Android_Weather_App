package com.example.zpo_weather_app;

import java.io.Serializable;

/**
 * represents the weather
 */
public class Weather implements Serializable {
    private String cityAndCountry;
    private String iconNumber;
    private String main;
    private  String description;
    private String pressure;
    private String wind;
    private String humidity;
    private int temperature;
    private int feelsTemperature;

    public Weather() {
    }



    public Weather(String cityAndCountry, String iconNumber, String main, String description, String pressure, String wind, String humidity, int temperature, int feelsTemperature) {
        this.cityAndCountry = cityAndCountry;
        this.iconNumber = iconNumber;
        this.main = main;
        this.description = description;
        this.pressure = pressure;
        this.wind = wind;
        this.humidity = humidity;
        this.temperature = temperature;
        this.feelsTemperature = feelsTemperature;
    }

    /**
     *
     * @return string of city and country
     */
    public String getCityAndCountry() {
        return cityAndCountry;
    }

    /**
     *
     * @return string of main weather description
     */
    public String getMain() {
        return main;
    }

    /**
     *
     * @return string of weather description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return the pressure
     */
    public String getPressure() {
        return pressure;
    }

    /**
     *
     * @return the speed wind
     */
    public String getWind() {
        return wind;
    }

    /**
     *
     * @return the value of humidity
     */
    public String getHumidity() {
        return humidity;
    }

    /**
     *
     * @return the value of temperature
     */
    public int getTemperature() {
        return temperature;
    }

    /**
     *
     * @return the value of feels temperature
     */
    public int getFeelsTemperature() {
        return feelsTemperature;
    }
}
