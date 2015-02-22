// GiftCards Android App
//
// Copyright (c) 2007-2015 GiftCards.com.  All rights reserved.

package com.jacmobile.weather.network;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.squareup.otto.Bus;

import com.jacmobile.weather.events.AEvent;
import com.jacmobile.weather.events.NetworkError;
import com.jacmobile.weather.events.NetworkEvent;
import com.jacmobile.weather.network.requests.GsonRequest;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;

public class NetworkService<T> implements NetworkProvider.NetworkService {
    private Bus bus;
    private Gson gson;
    private NetworkProvider networkProvider;

    public NetworkService(NetworkProvider networkProvider, Bus bus, Gson gson) {
        this.networkProvider = networkProvider;
        this.bus = bus;
        this.gson = gson;
    }

    @Override public void get(String id) {
        switch (id) {
//            case WEATHER_INFO_URL: GsonRequest(id, null, WeatherInfo.class); break;
        }
    }

    @Override public void post(String id, String json) {
        switch (id) {
//            case LOGIN_URL: GsonRequest(id, json, LoginData.class); break;
        }
    }

    private void bus(AEvent response) {
        bus.register(this);
        bus.post(response);
        bus.unregister(this);
    }

    private void add(String id, GsonRequest request) {
        Log.wtf("Adding request", "URL: " + id);
        request.setTag(id);
        networkProvider.addToRequestQueue(request);
    }

    private void GsonRequest(String id, String json, Class clazz) {
        if (json == null) {
            add(id, new GsonRequest(GET, id, clazz, null, success(), error(id), gson));
        } else {
            add(id, new GsonRequest(POST, id, clazz, json, success(), error(id), gson));
        }
    }

    private Response.Listener<T> success() {
        return new Response.Listener<T>() {
            @Override public void onResponse(T response) {
                bus((NetworkEvent) response);
            }
        };
    }

    private Response.ErrorListener error(final String requestId) {
        return new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError volleyError) {
                bus(new NetworkError(requestId, volleyError));
            }
        };
    }
}