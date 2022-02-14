package com.test.simpleweatherapp.data;

import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("id")
    public int lon;

    @SerializedName("main")
    public String main;

    @SerializedName("description")
    public String desc;

    @SerializedName("icon")
    public String icon;
}
