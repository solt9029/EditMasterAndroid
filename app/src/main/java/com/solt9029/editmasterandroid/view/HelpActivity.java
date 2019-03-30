package com.solt9029.editmasterandroid.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.solt9029.editmasterandroid.R;

public class HelpActivity extends AppCompatActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, HelpActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }
}
