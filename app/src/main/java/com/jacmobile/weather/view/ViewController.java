package com.jacmobile.weather.view;

import android.content.res.Resources;
import android.view.View;

import com.jacmobile.weather.activities.MainActivity;
import com.jacmobile.weather.events.Update;

public abstract class ViewController implements View.OnClickListener {
    private View root;
    private Update update;

    public abstract Update provideUpdate();

    public abstract void onCreateView(View root);

    public abstract void onResume();

    protected MainActivity context() {
        return (MainActivity) root.getContext();
    }

    protected String string(int resId) {
        return root.getContext().getString(resId);
    }

    protected Resources resources() {
        return context().getResources();
    }

    protected View view(int resId) {
        return context().findViewById(resId);
    }
}