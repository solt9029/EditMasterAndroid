package com.solt9029.editmasterandroid.di;

import com.solt9029.editmasterandroid.viewmodel.ScoreListViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class, AppModule.class})
public interface AppComponent {
    void inject(ScoreListViewModel target);
}