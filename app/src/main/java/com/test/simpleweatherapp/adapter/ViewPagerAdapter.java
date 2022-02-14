package com.test.simpleweatherapp.adapter;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.test.simpleweatherapp.WeatherDetailFragment;
import com.test.simpleweatherapp.data.City;

import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private List<City> cityList;

    public ViewPagerAdapter(Fragment fragment) {
        super(fragment);
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new WeatherDetailFragment(cityList.get(0));
            case 1:
                return new WeatherDetailFragment(cityList.get(1));
            case 2:
                return new WeatherDetailFragment(cityList.get(2));
        }
        return new WeatherDetailFragment();
    }

    @Override
    public int getItemCount() {
        return this.cityList.size();
    }
}
