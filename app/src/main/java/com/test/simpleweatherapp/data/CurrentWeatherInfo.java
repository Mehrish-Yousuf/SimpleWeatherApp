package com.test.simpleweatherapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeatherInfo {

    @SerializedName("lat")
    public float lat;

    @SerializedName("lon")
    public float lon;

    @SerializedName("hourly")
    public List<HourlyWeatherDetail> hourly;

    @SerializedName("daily")
    public List<DailyWeatherDetail> daily;

    @Override
    public String toString() {
        return "CurrentWeatherInfo";
    }
}

class Main {
    @SerializedName("temp")
    public float temp;
    @SerializedName("humidity")
    public float humidity;
    @SerializedName("pressure")
    public float pressure;
    @SerializedName("temp_min")
    public float temp_min;
    @SerializedName("temp_max")
    public float temp_max;
}
