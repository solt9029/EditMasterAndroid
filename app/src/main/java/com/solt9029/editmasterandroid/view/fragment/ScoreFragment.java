package com.solt9029.editmasterandroid.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.solt9029.editmasterandroid.R;
import com.solt9029.editmasterandroid.databinding.FragmentScoreBinding;
import com.solt9029.editmasterandroid.view.activity.ScoreActivity;
import com.solt9029.editmasterandroid.viewmodel.ScoreViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerFragment;

public class ScoreFragment extends DaggerFragment {
    @Inject
    ViewModelProvider.Factory factory;
    private FragmentScoreBinding binding;
    private YouTubePlayer youTubePlayer;
    private ScoreViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_score, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ScoreActivity activity = Objects.requireNonNull((ScoreActivity) getActivity());

        viewModel = ViewModelProviders.of(activity, factory).get(ScoreViewModel.class);
        binding.setViewModel(viewModel);

        // observe videoId
        ScoreFragment fragment = this;
        binding.youTubePlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NotNull YouTubePlayer _youTubePlayer) {
                super.onReady(_youTubePlayer);
                youTubePlayer = _youTubePlayer;
                viewModel.videoId.observe(fragment, videoId -> youTubePlayer.loadVideo(videoId, 0f));
            }
        });

        // editor scroll
        binding.scrollContainerView.setOnScrollChangeListener((x, y, oldX, oldY) -> {
            viewModel.translateY.setValue(y);
            binding.editorBarsView.draw(y);
        });
        binding.scrollContainerView.addOnLayoutChangeListener((view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            binding.editorBarsView.draw(viewModel.translateY.getValue());
        });

        // settings
        viewModel.navigateToScoreSettingsFragment.observe(this, it -> activity.navigateToScoreSettingsFragment());
    }
}
