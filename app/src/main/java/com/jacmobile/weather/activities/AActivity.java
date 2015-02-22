package com.jacmobile.weather.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import dagger.ObjectGraph;
import com.jacmobile.weather.injection.ActivityScopeModule;
import com.jacmobile.weather.injection.DaggerApplication;
import com.jacmobile.weather.injection.DaggerInjector;

public abstract class AActivity extends ActionBarActivity implements DaggerInjector {
    private ObjectGraph mActivityGraph;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerApplication application = (DaggerApplication) getApplication();
        mActivityGraph = application.getObjectGraph().plus(geActivitytModules());
        mActivityGraph.inject(this);
    }

    @Override protected void onDestroy() {
        mActivityGraph = null;
        super.onDestroy();
    }

    @Override public void inject(Object object) {
        mActivityGraph.inject(object);
    }

    @Override public ObjectGraph getObjectGraph() {
        return mActivityGraph;
    }

    protected Object[] geActivitytModules() {
        return new Object[]{
                new ActivityScopeModule(this),
        };
    }
}