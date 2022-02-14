package com.test.simpleweatherapp.api;

import com.test.simpleweatherapp.data.CurrentWeatherInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapService {

//    https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&exclude=current,minutely&appid=e8a0eea80fb3cf3b499343766222f5f8
    @GET("onecall") //i.e https://api.test.com/Search?
    Call<CurrentWeatherInfo> getCurrentWeather(
            @Query("appid") String apiKey,
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("exclude") List<String> excludes,
            @Query("units") String units);
}
