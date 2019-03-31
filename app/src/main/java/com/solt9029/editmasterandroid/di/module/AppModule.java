package com.solt9029.editmasterandroid.di.module;

import android.app.Application;
import android.content.Context;

import com.solt9029.editmasterandroid.repository.ScoreRepository;
import com.solt9029.editmasterandroid.service.ScoreService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {
    @Provides
    Context provideContext(Application application) {
        return application;
    }

    @Singleton
    @Provides
    ScoreService provideScoreService() {
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
    ScoreRepository provideScoreRepository(ScoreService service) {
        return new ScoreRepository(service);
    }
}