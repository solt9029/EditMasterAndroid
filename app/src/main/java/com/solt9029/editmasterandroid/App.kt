package com.solt9029.editmasterandroid

import com.mlykotom.valifi.ValiFi
import com.solt9029.editmasterandroid.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()

        ValiFi.install(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<App> {
        return DaggerAppComponent.builder().application(this).build()
    }
}
