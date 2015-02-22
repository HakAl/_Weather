package com.jacmobile.weather.events;

import com.android.volley.VolleyError;

public class NetworkError extends AEvent {
    private VolleyError error;

    public NetworkError(String data, VolleyError error) {
        super(data);
        this.error = error;
    }

    public VolleyError getError() {
        return error;
    }

    public void setError(VolleyError error) {
        this.error = error;
    }
}