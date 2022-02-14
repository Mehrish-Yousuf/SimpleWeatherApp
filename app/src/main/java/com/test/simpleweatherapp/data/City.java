package com.test.simpleweatherapp.data;


public class City {
    private String city_name;
    private String lat;
    private String lon;

    public City() {
    }

    public City(String cityName, double lat, double lon) {
        this.city_name = cityName;
        this.lat = String.valueOf(lat);
        this.lon = String.valueOf(lon);
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
