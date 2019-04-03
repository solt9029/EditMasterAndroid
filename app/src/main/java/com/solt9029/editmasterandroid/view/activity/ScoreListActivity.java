package com.solt9029.editmasterandroid.view.activity;


import android.content.Intent;
import android.os.Bundle;

import com.solt9029.editmasterandroid.R;

import dagger.android.support.DaggerAppCompatActivity;

public class ScoreListActivity extends DaggerAppCompatActivity {
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
}