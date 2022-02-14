package com.test.simpleweatherapp.api;

import com.test.simpleweatherapp.data.CurrentWeatherInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class OpenWeatherMap {

    private static final String DEFAULT_VERSION = "2.5";

    private final OpenWeatherMapService openWeatherMapService;
    private final String apiKey;

    public OpenWeatherMap(String apiKey) {
        this(apiKey, DEFAULT_VERSION);
    }

    public OpenWeatherMap(String apiKey, String apiVersion) {
        this.apiKey = apiKey;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/" + apiVersion + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        openWeatherMapService = retrofit.create(OpenWeatherMapService.class);
    }

    public CurrentWeatherInfo getCurrentWeather(double lat, double lon, List<String> excludes, String units) {
        try {
            Call<CurrentWeatherInfo> call = openWeatherMapService.getCurrentWeather(apiKey, lat, lon, excludes, units);
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void getCurrentWeather(double lat, double lon, List<String> excludes, String units, SuccessCallback<CurrentWeatherInfo> callback) {
        getCurrentWeather(lat, lon, excludes, units, callback, null);
    }

    public void getCurrentWeather(double lat, double lon, List<String> excludes, String units, final SuccessCallback<CurrentWeatherInfo> successCallback, final FailCallback failCallback) {
        openWeatherMapService.getCurrentWeather(apiKey, lat, lon, excludes, units).enqueue(new Callback<CurrentWeatherInfo>() {
            @Override
            public void onResponse(final Call<CurrentWeatherInfo> call, final Response<CurrentWeatherInfo> response) {
                if (successCallback != null) {
                    successCallback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(final Call<CurrentWeatherInfo> call, final Throwable t) {
                if (failCallback != null) {
                    failCallback.onFail(t);
                }
            }
        });
    }

    public interface SuccessCallback<T> {

        void onSuccess(T result);

    }

    public interface FailCallback {

        void onFail(Throwable e);

    }

}
