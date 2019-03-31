package com.solt9029.editmasterandroid.view.activity;


import android.content.Intent;
import android.os.Bundle;

import com.solt9029.editmasterandroid.R;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class ScoreListActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    @Inject
    DispatchingAndroidInjector<Fragment> injector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_list);
    }

    public void navigateToHelpActivity() {
        Intent intent = HelpActivity.createIntent(ScoreListActivity.this);
        startActivity(intent);
    }

    public void navigateToScoreActivity(Integer id) {
        Intent intent = ScoreActivity.createIntent(ScoreListActivity.this, id);
        startActivity(intent);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return injector;
    }
}