package com.jacmobile.weather.network.requests;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

import com.jacmobile.weather.network.NetworkConfig;

public class NetworkRequest extends JsonRequest<String> {
    public NetworkRequest(int method, String url, String json, Listener<String> success, ErrorListener error) {
        this(method, url, json, success, error, NetworkConfig.TIMEOUT, NetworkConfig.RETRY, NetworkConfig.BACKOFF);
    }

    public NetworkRequest(int method, String url, String json, Listener<String> success, ErrorListener error,
                          int timeout,
                          int retry,
                          int backoff) {
        super(method, url, json, success, error);
        super.setRetryPolicy(new DefaultRetryPolicy(timeout, retry, backoff));
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException ex) {
            return Response.error(new ParseError(ex));
        }
    }
}