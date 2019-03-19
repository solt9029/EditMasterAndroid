package com.solt9029.editmasterandroid.di;

import com.solt9029.editmasterandroid.view.ScoreListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class, AppModule.class, ViewModelModule.class})
public interface AppComponent {
    void inject(ScoreListActivity target);
}