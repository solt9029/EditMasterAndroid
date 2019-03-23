package com.solt9029.editmasterandroid.di;

import com.solt9029.editmasterandroid.view.ScoreListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract ScoreListActivity contributeScoreListActivity();
}
