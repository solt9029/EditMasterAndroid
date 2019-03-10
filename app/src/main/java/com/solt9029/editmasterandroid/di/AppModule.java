package com.solt9029.editmasterandroid.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
@Singleton
public class AppModule {
    private Context context;

    public AppModule(Application app) {
        context = app;
    }

    @Provides
    public CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    public Context provideContext() {
        return context;
    }
}