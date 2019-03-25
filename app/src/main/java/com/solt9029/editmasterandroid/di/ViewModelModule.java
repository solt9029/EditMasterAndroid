package com.solt9029.editmasterandroid.di;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.solt9029.editmasterandroid.viewmodel.ScoreListViewModel;
import com.solt9029.editmasterandroid.viewmodel.ScoreViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ScoreListViewModel.class)
    public abstract ViewModel bindScoreListViewModel(ScoreListViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ScoreViewModel.class)
    public abstract ViewModel bindScoreViewModel(ScoreViewModel viewModel);

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
