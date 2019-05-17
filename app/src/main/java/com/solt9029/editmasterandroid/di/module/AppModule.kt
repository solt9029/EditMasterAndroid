package com.solt9029.editmasterandroid.di.module

import android.app.Application
import android.content.Context
import com.solt9029.editmasterandroid.repository.ScoreRepository
import com.solt9029.editmasterandroid.service.ScoreService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton

@Module(includes = [ViewModelModule::class]) class AppModule {
    @Provides
    fun provideContext(application: Application): Context = application

    @Singleton @Provides
    fun provideScoreService(): ScoreService {
        val retrofit = Retrofit.Builder()
                .baseUrl(ScoreService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(ScoreService::class.java)
    }

    @Singleton @Provides
    fun provideScoreRepository(service: ScoreService): ScoreRepository = ScoreRepository(service)
}