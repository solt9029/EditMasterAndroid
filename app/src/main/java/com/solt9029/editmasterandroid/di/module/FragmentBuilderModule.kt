package com.solt9029.editmasterandroid.di.module

import com.solt9029.editmasterandroid.view.fragment.DialogFragment
import com.solt9029.editmasterandroid.view.fragment.ScoreFragment
import com.solt9029.editmasterandroid.view.fragment.ScoreListFragment
import com.solt9029.editmasterandroid.view.fragment.ScoreSettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module interface FragmentBuilderModule {
    @ContributesAndroidInjector
    fun contributeScoreListFragment(): ScoreListFragment

    @ContributesAndroidInjector
    fun contributeScoreFragment(): ScoreFragment

    @ContributesAndroidInjector
    fun contributeScoreSettingsFragment(): ScoreSettingsFragment

    @ContributesAndroidInjector
    fun contributeValidationErrorListDialogFragment(): DialogFragment
}
