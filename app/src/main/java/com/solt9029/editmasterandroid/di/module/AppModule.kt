package com.solt9029.editmasterandroid.di.module

import android.app.Application
import android.content.Context
import com.solt9029.editmasterandroid.repository.ScoreRepository
import com.solt9029.editmasterandroid.service.ScoreService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class]) class AppModule {
    @Provides
    fun provideContext(application: Application): Context = application

    @Singleton @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    @Singleton @Provides
    fun provideScoreService(client: OkHttpClient): ScoreService {
        val retrofit = Retrofit.Builder()
                .baseUrl(ScoreService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(ScoreService::class.java)
    }

    @Singleton @Provides
    fun provideScoreRepository(service: ScoreService): ScoreRepository = ScoreRepository(service)
}