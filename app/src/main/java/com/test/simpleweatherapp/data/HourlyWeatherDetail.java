package com.test.simpleweatherapp.data;

import com.google.gson.annotations.SerializedName;

public class HourlyWeatherDetail extends  WeatherDetail {

    @SerializedName("temp")
    public float temp;

    @SerializedName("feels_like")
    public float feels_like;

    @SerializedName("rain")
    public Rain rain;
}
