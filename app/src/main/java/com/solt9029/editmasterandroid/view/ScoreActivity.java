package com.solt9029.editmasterandroid.view;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.solt9029.editmasterandroid.R;
import com.solt9029.editmasterandroid.databinding.ActivityScoreBinding;
import com.solt9029.editmasterandroid.databinding.ActivityScoreListBinding;
import com.solt9029.editmasterandroid.di.Injectable;
import com.solt9029.editmasterandroid.viewmodel.ScoreListViewModel;
import com.solt9029.editmasterandroid.viewmodel.ScoreViewModel;

import javax.inject.Inject;

public class ScoreActivity extends AppCompatActivity implements Injectable {
    public static final String ID = "score_id"; // for intent.
    @Inject
    ViewModelProvider.Factory factory;
    private ScoreViewModel viewModel;

    public static Intent createIntent(Context context, Integer id) {
        return new Intent(context, ScoreActivity.class).putExtra(ID, id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        int id = getIntent().getIntExtra(ID, 0);

        viewModel = ViewModelProviders.of(this, factory).get(ScoreViewModel.class);
        viewModel.initScore(id);
        ActivityScoreBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_score);
        binding.setViewModel(viewModel);
    }
}
