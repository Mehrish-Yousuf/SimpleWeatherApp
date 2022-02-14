package com.test.simpleweatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.FragmentContainerView;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.test.simpleweatherapp.data.City;
import com.test.simpleweatherapp.services.CSVReadService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";

    private FragmentContainerView mainFragmentContainerView;
    private FragmentContainerView searchFragmentContainerView;

    private SimpleCursorAdapter mAdapter;

    private List<String[]> searchCitiesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragmentContainerView = findViewById(R.id.main_fragment_container_view);
        searchFragmentContainerView = findViewById(R.id.search_fragment_container_view);

        searchFragmentContainerView.setVisibility(View.GONE);

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("some_int", 0);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.main_fragment_container_view, MainFragmentContainer.class, bundle)
                    .commit();
        }

        searchCitiesList = new ArrayList<>();

        final String[] from = new String[] {"cityName", "country", "lat", "lon"};
        final int[] to = new int[] {android.R.id.text1, android.R.id.text2};
        mAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);

        MenuItem item = menu.findItem(R.id.searchActionItem);
        SearchView searchView = new SearchView(this);

        item.setActionView(searchView);
        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                searchCitiesList = getCitiesSearchList();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (mainFragmentContainerView.getVisibility() == View.GONE) {
                    searchFragmentContainerView.setVisibility(View.GONE);
                    mainFragmentContainerView.setVisibility(View.VISIBLE);
                }
                searchCitiesList = new ArrayList<>();
                return true;
            }
        });

        searchView.setSuggestionsAdapter(mAdapter);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
                Cursor cursor = (Cursor) mAdapter.getItem(position);
                int columnCityNameIndex = cursor.getColumnIndex("cityName");
                String cityName = cursor.getString(columnCityNameIndex);
                searchView.setQuery(cityName, true);
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                Log.d(TAG, String.valueOf(position));
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                if (mAdapter.getCount() > 0) {
                    City city = getCityFromAdapter(query);
                    Bundle bundle = new Bundle();

                    if (city != null) {
                        bundle.putString("cityName", city.getCity_name());
                        bundle.putDouble("lat", Double.parseDouble(city.getLat()));
                        bundle.putDouble("lon", Double.parseDouble(city.getLon()));
                    } else {
                        bundle.putString("cityName", "Karachi");
                        bundle.putDouble("lat", 24.9056);
                        bundle.putDouble("lon", 67.0822);
                    }

                    mainFragmentContainerView.setVisibility(View.GONE);
                    searchFragmentContainerView.setVisibility(View.VISIBLE);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.search_fragment_container_view, WeatherDetailFragment.class, bundle)
                            .addToBackStack(WeatherDetailFragment.class.getName())
                            .commit();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryText) {
                if (queryText.length() >= 2)
                    populateAdapter(queryText);
                return false;
            }
        });
//        return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mainFragmentContainerView.getVisibility() == View.GONE) {
            searchFragmentContainerView.setVisibility(View.GONE);
            mainFragmentContainerView.setVisibility(View.VISIBLE);
        }
        else {
            super.onBackPressed();
        }
    }

    List<City> parseCSV(){
        InputStream inputStream = getResources().openRawResource(R.raw.cities_all);
        CSVReadService csvFile = new CSVReadService(inputStream);
        List<String[]> citiesList;
        citiesList = csvFile.read();
        Log.d(TAG, "onViewCreated:" + citiesList);
        List<City> cityList = new ArrayList<City>();
        for(int i=1; i<citiesList.size(); i++) {
            String name = citiesList.get(i)[1];
            if(name.equals("Beijing") || name.equals("Rio de Janeiro") || name.equals("Los Angeles")){
                City city = new City();
                city.setCity_name(citiesList.get(i)[1]);
                city.setLat(citiesList.get(i)[5]);
                city.setLon(citiesList.get(i)[6]);
                cityList.add(city);
            }
        }
        return cityList;

    }

    private void populateAdapter(String query) {
        final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "cityName", "country", "lat", "lon" });
        for (int i=1; i<searchCitiesList.size(); i++) {
            String name = searchCitiesList.get(i)[1];
            if (name.toLowerCase().startsWith(query.toLowerCase())) {
                c.addRow(new Object[] { i, name, searchCitiesList.get(i)[4], searchCitiesList.get(i)[5], searchCitiesList.get(i)[6] });
            }
        }
        mAdapter.changeCursor(c);
    }

    // make sure that the Adapter count/size is more than 1
    private City getCityFromAdapter(String query) {

        for (int i=0; i<mAdapter.getCount(); i++) {
            Cursor cursor = (Cursor) mAdapter.getItem(i);
            int columnCityNameIndex = cursor.getColumnIndex("cityName");
            String cityName = cursor.getString(columnCityNameIndex);
            if (cityName.equalsIgnoreCase(query)) {
                int columnLatIndex = cursor.getColumnIndex("lat");
                int columnLonIndex = cursor.getColumnIndex("lon");
                return new City(cityName,
                        Double.parseDouble(cursor.getString(columnLatIndex)),
                        Double.parseDouble(cursor.getString(columnLonIndex)));
            }
        }

        // default case always send first item data when query does not match
        Cursor cursor = (Cursor) mAdapter.getItem(0);
        int columnCityNameIndex = cursor.getColumnIndex("cityName");
        int columnLatIndex = cursor.getColumnIndex("lat");
        int columnLonIndex = cursor.getColumnIndex("lon");

        return new City(cursor.getString(columnCityNameIndex),
                Double.parseDouble(cursor.getString(columnLatIndex)),
                Double.parseDouble(cursor.getString(columnLonIndex)));
    }

    private List<String[]> getCitiesSearchList() {
        InputStream inputStream = getResources().openRawResource(R.raw.cities_all);
        CSVReadService csvFile = new CSVReadService(inputStream);
        return csvFile.read();
    }
}