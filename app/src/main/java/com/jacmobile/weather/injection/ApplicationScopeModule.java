package com.jacmobile.weather.injection;

import dagger.Module;

@Module(
        complete = true,
        library = true,
        addsTo = AndroidAppModule.class,
        injects = {
                DaggerApplication.class
        }
)
public class ApplicationScopeModule {
}