package com.solt9029.editmasterandroid;

import com.mlykotom.valifi.ValiFi;
import com.solt9029.editmasterandroid.di.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import timber.log.Timber;

public class App extends DaggerApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        ValiFi.install(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    protected AndroidInjector<App> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
