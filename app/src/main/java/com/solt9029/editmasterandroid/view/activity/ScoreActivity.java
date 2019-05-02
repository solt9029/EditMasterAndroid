package com.solt9029.editmasterandroid.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.solt9029.editmasterandroid.R;
import com.solt9029.editmasterandroid.view.fragment.ScoreFragment;
import com.solt9029.editmasterandroid.view.fragment.ScoreSettingsFragment;
import com.solt9029.editmasterandroid.viewmodel.ScoreViewModel;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerAppCompatActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ScoreActivity extends DaggerAppCompatActivity {
    public static final String ID = "score_id"; // for intent.
    @Inject
    ViewModelProvider.Factory factory;

    public static Intent createIntent(Context context, Integer id) {
        return new Intent(context, ScoreActivity.class).putExtra(ID, id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        ScoreViewModel viewModel = ViewModelProviders.of(this, factory).get(ScoreViewModel.class);
        int id = this.getIntent().getIntExtra(ScoreActivity.ID, 0);
        viewModel.initScore(id);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new ScoreFragment(), "ScoreFragment")
                .commit();
    }

    public void navigateToScoreSettingsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("score")
                .replace(R.id.fragment_container, new ScoreSettingsFragment(), null)
                .commit();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
