package com.solt9029.editmasterandroid.di

import android.app.Application
import com.solt9029.editmasterandroid.App
import com.solt9029.editmasterandroid.di.module.ActivityBuilderModule
import com.solt9029.editmasterandroid.di.module.AppModule
import com.solt9029.editmasterandroid.di.module.FragmentBuilderModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBuilderModule::class,
    FragmentBuilderModule::class
])
interface AppComponent : AndroidInjector<App> {
    override fun inject(target: App)

    // https://starzero.hatenablog.com/entry/2017/05/21/225532
    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun build(): AppComponent
    }
}
