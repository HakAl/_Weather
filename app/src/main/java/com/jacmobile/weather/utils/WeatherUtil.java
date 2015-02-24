package com.jacmobile.weather.utils;

public class WeatherUtil {
    private static final float KELVIN = 273.15f;

    /** Kelvin: ºF = (K - 273.15) * 1.8000 + 32.00 **/
    public static float kelvinToF(float degreesK)
    {
        return (degreesK - KELVIN) * 1.8f + 32f;
    }

    public static float kelvenToC(float degreesK)
    {
        return degreesK - KELVIN;
    }
}