package com.test.simpleweatherapp;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.test.simpleweatherapp.adapter.ViewPagerAdapter;
import com.test.simpleweatherapp.data.City;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainFragmentContainer extends Fragment {

    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private TabLayout tabLayout;

    public static MainFragmentContainer newInstance() {
        return new MainFragmentContainer();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment_container_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<City> cityList = new ArrayList<>();

        cityList.add(new City("Rio de Janeiro", -22.90278, -43.2075));
        cityList.add(new City("Beijing", 39.9075, -116.39723));
        cityList.add(new City("Los Angeles", 34.0239, -118.17202));

        viewPager = view.findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPagerAdapter.setCityList(cityList);
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout = view.findViewById(R.id.tabLayout);
        for(City city : cityList){

            if(city.getCity_name().equalsIgnoreCase("Rio de Janeiro"))
                tabLayout.addTab(tabLayout.newTab().setText("Rio de Janeiro"));
            else if (city.getCity_name().equalsIgnoreCase("Beijing"))
                tabLayout.addTab(tabLayout.newTab().setText("Beijing"));
            else
                tabLayout.addTab(tabLayout.newTab().setText("Los Angeles"));

        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });
    }


}