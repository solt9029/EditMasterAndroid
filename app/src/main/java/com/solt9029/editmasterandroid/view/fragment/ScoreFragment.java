package com.solt9029.editmasterandroid.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.solt9029.editmasterandroid.R;
import com.solt9029.editmasterandroid.databinding.FragmentScoreBinding;
import com.solt9029.editmasterandroid.view.activity.ScoreActivity;
import com.solt9029.editmasterandroid.viewmodel.ScoreViewModel;
import dagger.android.support.DaggerFragment;

import javax.inject.Inject;
import java.util.Objects;

public class ScoreFragment extends DaggerFragment {
    @Inject
    ViewModelProvider.Factory factory;
    private FragmentScoreBinding binding;
    private ScoreViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ScoreActivity activity = Objects.requireNonNull((ScoreActivity) getActivity());

        viewModel = ViewModelProviders.of(activity, factory).get(ScoreViewModel.class);
        binding.setViewModel(viewModel);

        binding.setLifecycleOwner(this);

        viewModel.navigateToScoreSettingsFragment.observe(this, it -> activity.navigateToScoreSettingsFragment());
    }
}
