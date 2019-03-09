package com.solt9029.editmasterandroid.di;

import com.solt9029.editmasterandroid.service.ScoreService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@Singleton
public class ApiModule {
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
}