// GiftCards Android App
//
// Copyright (c) 2007-2015 GiftCards.com.  All rights reserved.

package com.jacmobile.weather.network.requests;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

public class GsonRequest<T> extends JsonRequest<T> {
    private Gson gson;
    private final Class<T> clazz;
    private final Response.Listener<T> listener;

    public GsonRequest(int method, String url, Class<T> clazz, String json,
                       Response.Listener<T> listener,
                       Response.ErrorListener errorListener,
                       Gson gson) {
        super(method, url, json, listener, errorListener);
        this.clazz = clazz;
        this.listener = listener;
        this.gson = gson;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}