package com.solt9029.editmasterandroid.di;

import android.app.Application;

public class AppApplication extends Application {
    private AppComponent component;
    private static AppApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        component = DaggerAppComponent.builder().application(this).build();
    }

    public AppComponent getComponent() {
        return component;
    }

    public static AppApplication getApplication() {
        return application;
    }
}
