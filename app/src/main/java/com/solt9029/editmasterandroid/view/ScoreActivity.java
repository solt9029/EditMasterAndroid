package com.solt9029.editmasterandroid.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.solt9029.editmasterandroid.R;

public class ScoreActivity extends AppCompatActivity {
    public static final String ID = "score_id"; // for intent.

    public static Intent createIntent(Context context) {
        return new Intent(context, ScoreActivity.class);
    }

    public static Intent createIntent(Context context, int id) {
        return new Intent(context, ScoreActivity.class).putExtra(ID, id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        int id = getIntent().getIntExtra(ID, 0);
    }
}
