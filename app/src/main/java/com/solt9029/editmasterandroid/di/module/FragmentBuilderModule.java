package com.solt9029.editmasterandroid.di.module;

import com.solt9029.editmasterandroid.view.fragment.ScoreFragment;
import com.solt9029.editmasterandroid.view.fragment.ScoreListFragment;
import com.solt9029.editmasterandroid.view.fragment.ScoreSettingsFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract ScoreListFragment contributeScoreListFragment();

    @ContributesAndroidInjector
    abstract ScoreFragment contributeScoreFragment();

    @ContributesAndroidInjector
    abstract ScoreSettingsFragment contributeScoreSettingsFragment();
}
