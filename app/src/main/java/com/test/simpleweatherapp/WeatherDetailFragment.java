package com.test.simpleweatherapp;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.simpleweatherapp.adapter.DailyRecyclerViewAdapter;
import com.test.simpleweatherapp.adapter.HourlyRecyclerViewAdapter;
import com.test.simpleweatherapp.api.OpenWeatherMap;
import com.test.simpleweatherapp.data.City;
import com.test.simpleweatherapp.data.CurrentWeatherInfo;
import com.test.simpleweatherapp.data.DailyWeatherDetail;
import com.test.simpleweatherapp.data.HourlyWeatherDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherDetailFragment extends Fragment {

    private static final String TAG = "Weather Detail Fragment";

    private RecyclerView hourlyrecyclerView;
    private RecyclerView dailyrecyclerView;
    private TextView updatedAtTextView;
    private ProgressBar spinner;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout hourlyLinearLayout;
    private LinearLayout dailyLinearLayout;

    private List<DailyWeatherDetail> dailyWeatherDetailList;
    private DailyRecyclerViewAdapter dailyRecyclerViewAdapter;

    private List<HourlyWeatherDetail> hourlyWeatherDetailList;
    private HourlyRecyclerViewAdapter hourlyRecyclerViewAdapter;

    private City city;

    public static WeatherDetailFragment newInstance() {
        return new WeatherDetailFragment();
    }

    public WeatherDetailFragment() {

    }

    public WeatherDetailFragment(City city) {
        this.city = city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            if (this.city == null) {
                this.city = new City(bundle.getString("cityName", ""),
                        bundle.getDouble("lat", 0.0),
                        bundle.getDouble("lon", 0.0));
            }
        }

        if (city == null) {
            this.city = new City("Karachi", 24.9056, 67.0822);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        spinner = (ProgressBar) view.findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);

        hourlyLinearLayout = view.findViewById(R.id.hourlyLinearLayout);
        hourlyLinearLayout.setVisibility(View.GONE);
        dailyLinearLayout = view.findViewById(R.id.dailyLinearLayout);
        dailyLinearLayout.setVisibility(View.GONE);

        dailyrecyclerView = view.findViewById(R.id.dailyRecyclerView);
        hourlyrecyclerView = view.findViewById(R.id.hourlyRecyclerView);
        updatedAtTextView = view.findViewById(R.id.updatedAtTextView);

        dailyWeatherDetailList = new ArrayList<DailyWeatherDetail>();
        hourlyWeatherDetailList = new ArrayList<HourlyWeatherDetail>();

        dailyRecyclerViewAdapter = new DailyRecyclerViewAdapter(dailyWeatherDetailList);
        dailyrecyclerView.setAdapter(dailyRecyclerViewAdapter);
        dailyrecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        hourlyRecyclerViewAdapter = new HourlyRecyclerViewAdapter(hourlyWeatherDetailList);
        hourlyrecyclerView.setAdapter(hourlyRecyclerViewAdapter);
        hourlyrecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(),
                LinearLayoutManager.HORIZONTAL, false));

        this.fetchWeatherData();

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(TAG, "onRefresh called from SwipeRefreshLayout");
                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        fetchWeatherData();
                    }
                }
        );
    }


    private void fetchWeatherData() {
        if (this.city != null) {
            OpenWeatherMap openWeatherMap = new OpenWeatherMap(BuildConfig.API_KEY);
            double lat = Double.parseDouble(city.getLat());
            double lon = Double.parseDouble(city.getLon());
            String units = "imperial";
            List<String> excludes = new ArrayList<String>();
            excludes.add("current");
            excludes.add("minutely");
            openWeatherMap.getCurrentWeather(lat, lon, excludes, units, new OpenWeatherMap.SuccessCallback<CurrentWeatherInfo>() {
                @Override
                public void onSuccess(CurrentWeatherInfo result) {

                    for(int i=1;i<6; i++){
                        dailyWeatherDetailList.add(result.daily.get(i));
                    }

                    dailyRecyclerViewAdapter.notifyDataSetChanged();
                    hourlyWeatherDetailList.addAll(result.hourly);
                    hourlyRecyclerViewAdapter.notifyDataSetChanged();

                    SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");
                    Date current = new Date();

                    format = new SimpleDateFormat("MMM dd hh:mm a");
                    String date = format.format(current);

                    String updatedAtString = "Last updated on: " + date;

                    updatedAtTextView.setText(updatedAtString);

                    Log.d(TAG, "onCreate: " + result.toString());
                    swipeRefreshLayout.setRefreshing(false);
                    spinner.setVisibility(View.GONE);
                    dailyLinearLayout.setVisibility(View.VISIBLE);
                    hourlyLinearLayout.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}