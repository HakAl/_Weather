// GiftCards Android App
//
// Copyright (c) 2007-2015 GiftCards.com.  All rights reserved.
package com.jacmobile.weather.network;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonRequest;

public class NetworkProvider {
    private RequestQueue requestQueue;

    public NetworkProvider(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void addToRequestQueue(JsonRequest<String> request) {
        this.requestQueue.add(request);
    }

    public void addToRequestQueue(Request<String> request) {
        this.requestQueue.add(request);
    }

    /**
     * Cancel request by String tag *
     */
    public void cancelRequest(String requestId) {
        this.requestQueue.cancelAll(requestId);
    }

    /**
     * @param requestIds The tags associated with pending requests *
     */
    public void cancelAllRequests(String... requestIds) {
        for (String tag : requestIds) {
            this.requestQueue.cancelAll(tag);
        }
    }

    /**
     * @param key The cache key
     * @return The item that is stored in cache
     */
    public Cache.Entry getCachedItem(String key) {
        return this.requestQueue.getCache().get(key);
    }

    /**
     * @param key  The cache key
     * @param data The item to store
     */
    public void addCacheEntry(String key, Cache.Entry data) {
        this.requestQueue.getCache().put(key, data);
    }

    public interface NetworkService {
        public void get(String requestId);

        public void post(String requestId, String json);
    }
}