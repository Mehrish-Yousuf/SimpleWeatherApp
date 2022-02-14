package com.test.simpleweatherapp.data;

import com.google.gson.annotations.SerializedName;

public class DailyWeatherDetail extends  WeatherDetail {

    @SerializedName("temp")
    public TempDaily temp;

    @SerializedName("feels_like")
    public FeelsLikeDaily feelsLike;

    @SerializedName("rain")
    public float rain;
}

