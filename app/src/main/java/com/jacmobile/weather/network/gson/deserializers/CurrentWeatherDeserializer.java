package com.jacmobile.weather.network.gson.deserializers;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.jacmobile.weather.events.CurrentWeather;

import java.lang.reflect.Type;

/**
 * {
     "coord": {
     "lon": 77,
     "lat": 35
     },

     "sys": {
     "message": 0.0934,
     "country": "IN",
 //todo
     "sunrise": 1424568593,
     "sunset": 1424608863
     },

     "weather": [
         {
         "id": 802,
         "main": "Clouds",
         "description": "scattered clouds",
         "icon": "03n"
         }
     ],

 "base": "cmc stations",

     "main": {
     "temp": 246.599,
     "temp_min": 246.599,
     "temp_max": 246.599,
     "pressure": 528.62,
     "sea_level": 1041.07,
     "grnd_level": 528.62,
     "humidity": 60
     },

     "wind": {
    "speed": 0.41,
    "deg": 232.509
    },

     "clouds": {
     "all": 36
     },

 "dt": 1424630642,
 "id": 1163626,
 "name": "Thang",
 "cod": 200
 }
 */

public class CurrentWeatherDeserializer implements JsonDeserializer<CurrentWeather> {

    @Override public CurrentWeather deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        CurrentWeather result = new CurrentWeather();
        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject coordObject = jsonObject.get("coord").getAsJsonObject();
        JsonObject mainObject = jsonObject.get("main").getAsJsonObject();
        result.setLatitude(coordObject.get("lat").getAsString());
        result.setLongitude(coordObject.get("lon").getAsString());
        result.setData(jsonObject.toString());
        result.setCity(jsonObject.get("name").getAsString());
        JsonArray temp = jsonObject.get("weather").getAsJsonArray();
        JsonObject weatherObject = temp.get(0).getAsJsonObject();
        result.setPrimaryDescription(weatherObject.get("main").getAsString());
        result.setSecondaryDescription(weatherObject.get("description").getAsString());
        result.setImageId(weatherObject.get("icon").getAsString());
        result.setTemp(mainObject.get("temp").getAsString());
        result.setTempMin(mainObject.get("temp_min").getAsString());
        result.setTempMax(mainObject.get("temp_max").getAsString());
        result.setPressure(mainObject.get("pressure").getAsString());
        result.setSeaLevel(mainObject.get("sea_level").getAsString());
        result.setGroundLevel(mainObject.get("grnd_level").getAsString());
        result.setHumidity(mainObject.get("humidity").getAsString());
        result.setWindSpeed(jsonObject.get("wind").getAsJsonObject().get("speed").getAsString());

        return result;
    }
}