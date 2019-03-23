package com.solt9029.editmasterandroid.di;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.solt9029.editmasterandroid.viewmodel.ScoreListViewModel;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ScoreListViewModel.class)
    public abstract ViewModel bindScoreListViewModel(ScoreListViewModel viewModel);
}
