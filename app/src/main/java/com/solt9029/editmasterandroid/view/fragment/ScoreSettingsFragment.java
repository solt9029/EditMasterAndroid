package com.solt9029.editmasterandroid.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.solt9029.editmasterandroid.R;
import com.solt9029.editmasterandroid.databinding.FragmentScoreBinding;
import com.solt9029.editmasterandroid.view.activity.ScoreActivity;
import com.solt9029.editmasterandroid.viewmodel.ScoreViewModel;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerFragment;

public class ScoreSettingsFragment extends DaggerFragment {
//    @Inject
//    ViewModelProvider.Factory factory;
//    private FragmentScoreBinding binding;
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score, container, false);
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        ScoreActivity activity = Objects.requireNonNull((ScoreActivity) getActivity());
//
//        ScoreViewModel viewModel = ViewModelProviders.of(activity, factory).get(ScoreViewModel.class);
//        int id = activity.getIntent().getIntExtra(ScoreActivity.ID, 0);
//        viewModel.initScore(id);
//        binding.setViewModel(viewModel);
//    }
}
