package com.solt9029.editmasterandroid.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.solt9029.editmasterandroid.R;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class ScoreActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    public static final String ID = "score_id"; // for intent.

    @Inject
    DispatchingAndroidInjector<Fragment> injector;

    public static Intent createIntent(Context context, Integer id) {
        return new Intent(context, ScoreActivity.class).putExtra(ID, id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return injector;
    }
}
