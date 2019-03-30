package com.solt9029.editmasterandroid.di;

import com.solt9029.editmasterandroid.view.ScoreListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract ScoreListFragment contributeScoreListFragment();

//    @ContributesAndroidInjector
//    abstract ScoreFragment contributeScoreFragment();
}
