package com.solt9029.editmasterandroid.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solt9029.editmasterandroid.di.ViewModelFactory
import com.solt9029.editmasterandroid.di.ViewModelKey
import com.solt9029.editmasterandroid.viewmodel.ScoreListViewModel
import com.solt9029.editmasterandroid.viewmodel.ScoreViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module interface ViewModelModule {
    @Binds @IntoMap @ViewModelKey(ScoreListViewModel::class)
    fun bindScoreListViewModel(viewModel: ScoreListViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(ScoreViewModel::class)
    fun bindScoreViewModel(viewModel: ScoreViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
