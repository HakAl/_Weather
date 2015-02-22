package com.jacmobile.weather.events;

public class Update extends AEvent {
    private String data;

    @Override public String getData() {
        return data;
    }

    @Override public void setData(String data) {
        this.data = data;
    }
}