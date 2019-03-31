package com.solt9029.editmasterandroid.di.module;

import com.solt9029.editmasterandroid.view.activity.ScoreActivity;
import com.solt9029.editmasterandroid.view.activity.ScoreListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract ScoreListActivity contributeScoreListActivity();

    @ContributesAndroidInjector
    abstract ScoreActivity contributeScoreActivity();
}
