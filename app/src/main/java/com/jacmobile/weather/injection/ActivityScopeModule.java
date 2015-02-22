package com.jacmobile.weather.injection;

import android.app.Activity;
import android.content.Context;

import com.jacmobile.weather.activities.AActivity;
import com.jacmobile.weather.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        complete = true,
        library = true,
        addsTo = ApplicationScopeModule.class,
        injects = {
        }
)
public class ActivityScopeModule {

    private final AActivity mActivity;

    public ActivityScopeModule(AActivity activity) {
        mActivity = activity;
    }

    @Provides
    @Singleton
    @ForActivity Context providesActivityContext() {
        return mActivity;
    }

    @Provides
    @Singleton Activity providesActivity() {
        return mActivity;
    }
}
