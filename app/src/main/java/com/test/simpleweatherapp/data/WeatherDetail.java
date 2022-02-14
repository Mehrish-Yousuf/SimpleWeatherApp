package com.test.simpleweatherapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherDetail {

    @SerializedName("dt")
    public long dt;

    @SerializedName("pressure")
    public float pressure;

    @SerializedName("humidity")
    public float humidity;

    @SerializedName("dew_point")
    public float dewPoint;

    @SerializedName("uvi")
    public float uvi;

    @SerializedName("clouds")
    public float clouds;

    @SerializedName("visibility")
    public float visibility;

    @SerializedName("wind_speed")
    public float windSpeed;

    @SerializedName("wind_deg")
    public float windDeg;

    @SerializedName("wind_gust")
    public float windGust;

    @SerializedName("weather")
    public List<Weather> weather;

    @SerializedName("pop")
    public float pop;
}
