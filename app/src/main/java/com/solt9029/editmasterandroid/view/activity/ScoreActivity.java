package com.solt9029.editmasterandroid.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.solt9029.editmasterandroid.R;

import dagger.android.support.DaggerAppCompatActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ScoreActivity extends DaggerAppCompatActivity {
    public static final String ID = "score_id"; // for intent.

    public static Intent createIntent(Context context, Integer id) {
        return new Intent(context, ScoreActivity.class).putExtra(ID, id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
