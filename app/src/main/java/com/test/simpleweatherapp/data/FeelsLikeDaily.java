package com.test.simpleweatherapp.data;

import com.google.gson.annotations.SerializedName;

public class FeelsLikeDaily {

    @SerializedName("day")
    public float day;

    @SerializedName("night")
    public float night;

    @SerializedName("eve")
    public float eve;

    @SerializedName("morn")
    public float morn;
}
