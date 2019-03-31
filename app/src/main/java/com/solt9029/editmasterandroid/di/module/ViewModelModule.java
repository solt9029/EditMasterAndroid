package com.solt9029.editmasterandroid.di.module;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.solt9029.editmasterandroid.di.ViewModelFactory;
import com.solt9029.editmasterandroid.di.ViewModelKey;
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
