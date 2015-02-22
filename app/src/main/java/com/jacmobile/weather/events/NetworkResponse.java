package com.jacmobile.weather.events;

import com.android.volley.VolleyError;

public class NetworkResponse extends AEvent {
    protected String data;
    protected VolleyError error;

    public NetworkResponse() { }

    public NetworkResponse(String data) {
        this.data = data;
    }

    public NetworkResponse(String data, VolleyError error) {
        this.data = data;
        this.error = error;
    }

    public VolleyError getError() {
        return error;
    }

    public void setError(VolleyError error) {
        this.error = error;
    }

    @Override public String getData() {
        return data;
    }

    @Override public void setData(String data) {
        this.data = data;
    }
}