package com.solt9029.editmasterandroid.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;

import com.solt9029.editmasterandroid.service.ScoreService;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    private Context context;

    public AppModule(Application application) {
        context = application;
    }

    @Provides
    public CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    public ScoreService provideScoreService() {
        final Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(ScoreService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(ScoreService.class);
    }

    @Singleton
    @Provides
    public ViewModelProvider.Factory provideViewModelFactory(ViewModelFactory factory) {
        return factory;
    }
}