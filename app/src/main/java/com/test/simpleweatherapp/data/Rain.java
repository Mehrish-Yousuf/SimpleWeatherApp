package com.test.simpleweatherapp.data;

import com.google.gson.annotations.SerializedName;

public class Rain {

    @SerializedName("1h")
    public float h1;

    @SerializedName("3h")
    public float h3;
}
