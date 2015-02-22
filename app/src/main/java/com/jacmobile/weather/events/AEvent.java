package com.jacmobile.weather.events;

public abstract class AEvent {
    protected String data;

    public AEvent() {
    }

    public AEvent(String data) {

        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}