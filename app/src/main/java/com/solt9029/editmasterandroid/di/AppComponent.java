package com.solt9029.editmasterandroid.di;

import android.app.Application;

import com.solt9029.editmasterandroid.view.ScoreListActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ViewModelModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(ScoreListActivity target);
}
