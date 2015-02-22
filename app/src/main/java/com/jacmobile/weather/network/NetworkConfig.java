package com.jacmobile.weather.network;

public class NetworkConfig {

    public static final int TIMEOUT = 2500;
    public static final int RETRY = 3;
    public static final int BACKOFF = 2;


    //    Urls
    public static final String URL_WEATHER_BY_CITY = "http://api.openweathermap.org/data/2.5/weather?q=%s";

    public static final String URL_WEATHER_LAT_LONG = "http://api.openweathermap.org/data/2.5/weather?lat=%1$s&lon=%2$s";
}