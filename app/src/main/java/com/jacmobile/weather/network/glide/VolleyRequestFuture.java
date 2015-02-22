// GiftCards Android App
//
// Copyright (c) 2007-2015 GiftCards.com.  All rights reserved.

package com.jacmobile.weather.network.glide;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * TODO: contribute cancel modifications to volley and remove this class.
 *
 * A Future that represents a Volley request.
 *
 * Used by providing as your response and errors listeners. For example:
 * <pre>
 * RequestFuture&lt;JSONObject&gt; future = RequestFuture.newFuture();
 * MyRequest request = new MyRequest(URL, future, future);
 *
 * // If you want to be able to cancel the request:
 * future.setRequest(requestQueue.add(request));
 *
 * // Otherwise:
 * requestQueue.add(request);
 *
 * try {
 *   JSONObject response = future.get();
 *   // do something with response
 * } catch (InterruptedException e) {
 *   // handle the errors
 * } catch (ExecutionException e) {
 *   // handle the errors
 * }
 * </pre>
 *
 * @param <T> The type of parsed response this future expects.
 */
public class VolleyRequestFuture<T> implements Future<T>, Response.Listener<T>, Response.ErrorListener {
    private Request<?> mRequest;
    private boolean mResultReceived = false;
    private T mResult;
    private VolleyError mException;
    private boolean mIsCancelled = false;

    public static <E> VolleyRequestFuture<E> newFuture() {
        return new VolleyRequestFuture<E>();
    }

    public synchronized void setRequest(Request<?> request) {
        mRequest = request;
        if (mIsCancelled && mRequest != null) {
            mRequest.cancel();
        }
    }

    @Override
    public synchronized boolean cancel(boolean mayInterruptIfRunning) {
        if (isDone()) {
            return false;
        }
        mIsCancelled = true;
        if (mRequest != null) {
            mRequest.cancel();
        }
        notifyAll();

        return true;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        try {
            return doGet(null);
        } catch (TimeoutException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public T get(long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        return doGet(TimeUnit.MILLISECONDS.convert(timeout, unit));
    }

    private synchronized T doGet(Long timeoutMs)
            throws InterruptedException, ExecutionException, TimeoutException {
        if (mException != null) {
            throw new ExecutionException(mException);
        }

        if (mResultReceived) {
            return mResult;
        }

        if (isCancelled()) {
            throw new CancellationException();
        }

        if (timeoutMs == null) {
            wait(0);
        } else if (timeoutMs > 0) {
            wait(timeoutMs);
        }

        if (mException != null) {
            throw new ExecutionException(mException);
        }

        if (isCancelled()) {
            throw new CancellationException();
        }

        if (!mResultReceived) {
            throw new TimeoutException();
        }

        return mResult;
    }

    @Override
    public boolean isCancelled() {
        return mIsCancelled;
    }

    @Override
    public synchronized boolean isDone() {
        return mResultReceived || mException != null || isCancelled();
    }

    @Override
    public synchronized void onResponse(T response) {
        mResultReceived = true;
        mResult = response;
        notifyAll();
    }

    @Override
    public synchronized void onErrorResponse(VolleyError error) {
        mException = error;
        notifyAll();
    }
}
