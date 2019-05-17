package com.solt9029.editmasterandroid.di.module

import com.solt9029.editmasterandroid.view.activity.ScoreActivity
import com.solt9029.editmasterandroid.view.activity.ScoreListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module interface ActivityBuilderModule {
    @ContributesAndroidInjector
    fun contributeScoreListActivity(): ScoreListActivity

    @ContributesAndroidInjector
    fun contributeScoreActivity(): ScoreActivity
}
