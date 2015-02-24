package com.jacmobile.weather.views;

import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jacmobile.weather.utils.WeatherUtil;
import com.jacmobile.weather.events.AEvent;
import com.jacmobile.weather.events.CurrentWeather;
import com.jacmobile.weather.events.NetworkResponse;
import com.jacmobile.weather.events.Update;
import com.jacmobile.weather.network.NetworkConfig;
import com.jacmobile.weather.network.NetworkService;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import jacmobile.com.weather.R;

@Singleton public class CurrentWeatherView extends ViewController {
    @Inject Bus bus;
    @Inject LocationManager locationManager;
    @Inject NetworkService networkService;

    private ImageView imgWeather;
    private TextView txtCity, txtTemperature, txtPrimary, txtSecondary;
    private Map<String, Integer> imageMap = null;

    @Inject CurrentWeatherView() { }

    @Override public Update provideUpdate() {
        return null;
    }

    @Override public void onCreateView(View root) {
        this.root = root;
        this.imgWeather = (ImageView) root.findViewById(R.id.img_current_weather);
        this.txtCity = (TextView) root.findViewById(R.id.txt_current_city);
        this.txtTemperature = (TextView) root.findViewById(R.id.txt_current_temperature);
        this.txtPrimary = (TextView) root.findViewById(R.id.txt_current_primary);
        this.txtSecondary = (TextView) root.findViewById(R.id.txt_current_secondary);

        this.setImageMap();
    }

    @Override public void onResume() {
        bus.register(this);
        if (getLastLocation() != null) {
            Location lastLocation = getLastLocation();
            networkService.get(String.format(NetworkConfig.URL_WEATHER_LAT_LONG,
                    lastLocation.getLatitude() + "", lastLocation.getLongitude() + ""));
        } else {
            networkService.get(String.format(NetworkConfig.URL_WEATHER_LAT_LONG,
                    "65.9667", "-18.5333"));
        }
    }

    public void onStop()
    {
        bus.unregister(this);
    }

    @Override public void onClick(View v) {

    }

    @Subscribe public void busEvent(AEvent event) {
        if (event instanceof NetworkResponse) {
            bus.unregister(this);
            if (((NetworkResponse) event).getError() != null) {

            } else {
                if (event instanceof CurrentWeather) {
                    CurrentWeather current = (CurrentWeather) event;
                    txtCity.setText(current.getCity());
                    float f = WeatherUtil.kelvinToF(Float.valueOf(current.getTemp()));
                    String temp = String.format("%.1f", f);
                    txtTemperature.setText(temp + string(R.string.degree));
                    txtPrimary.setText(current.getPrimaryDescription());
                    txtSecondary.setText(current.getSecondaryDescription());
                    setImage(current.getImageId());
                }
            }
        }
    }

    /**
     *
     clear sky (d & n) 01d - 01n
     few clouds (d & n) 02d -02n
     scattered clouds  03
     broken clouds 04
     shower rain 09
     rain (d & n) 10d - 10n
     thunderstorm 11
     snow 13
     mist 50


     */


    private void setImage(String which)
    {
        imgWeather.setBackgroundResource(imageMap.get(which));
        if (which.endsWith("n")) {
            root.findViewById(R.id.parent_fragment_current).setBackgroundResource(R.color.secondary_text);
        } else if (which == "01d" || which == "02d" || which == "03d" || which == "04d") {
            root.findViewById(R.id.parent_fragment_current).setBackgroundResource(R.color.primary);
        }

    }

    private Location getLastLocation() {
        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

        long NetLocationTime = 0;

        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        if ( 0 < GPSLocationTime - NetLocationTime ) {
            return locationGPS;
        }
        else {
            return locationNet;
        }
    }

    private void setImageMap()
    {
        if (imageMap == null) {
            imageMap = new HashMap<>();
            imageMap.put("01d", R.drawable.clear_sky_day);
            imageMap.put("01n", R.drawable.clear_sky_night);
            imageMap.put("02d", R.drawable.some_clouds_day);
            imageMap.put("02n", R.drawable.some_clouds_night);
            imageMap.put("03n", R.drawable.scattered_clouds);
            imageMap.put("03d", R.drawable.scattered_clouds);
            imageMap.put("04d", R.drawable.scattered_clouds);
            imageMap.put("04n", R.drawable.scattered_clouds);
            imageMap.put("09n", R.drawable.rain_night);
            imageMap.put("09d", R.drawable.rain_day);
            imageMap.put("10d", R.drawable.rain_day);
            imageMap.put("10n", R.drawable.rain_night);
            imageMap.put("13n", R.drawable.snow);
            imageMap.put("13d", R.drawable.snow);
            imageMap.put("50d", R.drawable.mist);
            imageMap.put("50n", R.drawable.mist);
        }
    }
}