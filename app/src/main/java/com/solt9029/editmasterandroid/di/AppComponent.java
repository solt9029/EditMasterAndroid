package com.solt9029.editmasterandroid.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilderModule.class})
public interface AppComponent {

    void inject(AppApplication target);

    // https://starzero.hatenablog.com/entry/2017/05/21/225532
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
