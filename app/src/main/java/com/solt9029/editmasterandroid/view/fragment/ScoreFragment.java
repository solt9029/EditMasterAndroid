package com.solt9029.editmasterandroid.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.solt9029.editmasterandroid.R;
import com.solt9029.editmasterandroid.databinding.FragmentScoreBinding;
import com.solt9029.editmasterandroid.util.CalcUtil;
import com.solt9029.editmasterandroid.util.SafeUnboxUtil;
import com.solt9029.editmasterandroid.view.activity.ScoreActivity;
import com.solt9029.editmasterandroid.viewmodel.ScoreViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerFragment;
import timber.log.Timber;

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

        viewModel.navigateToScoreSettingsFragment.observe(this, it -> activity.navigateToScoreSettingsFragment());

        ScoreFragment fragment = this;
        binding.youTubePlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                fragment.youTubePlayer = youTubePlayer;
                viewModel.videoId.observe(fragment, videoId -> youTubePlayer.loadVideo(videoId, 0f));
            }
        });

        binding.editorBarsView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                draw();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                draw();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });

        binding.scrollContainerView.setOnScrollChangeListener((x, y, oldX, oldY) -> {
            viewModel.translateY.setValue(y);
            draw();
        });
        viewModel.notes.observe(this, notes -> {
            Timber.d("notes observed");
            draw();

            if (getContext() == null) {
                return;
            }
            ViewGroup.LayoutParams params = binding.relativeLayout.getLayoutParams();
            params.height = (int) CalcUtil.convertDp2Px((int) (notes.size() / 96.0) * 100 + 50, getContext());
            binding.relativeLayout.setLayoutParams(params);
        });

        binding.scrollContainerView.setOnTouchListener((view, event) -> {
            view.performClick();
            float x = event.getX();
            float y = event.getY();
            Timber.d("x" + x + ",y" + y);
            // update notes here.
            return false;
        });
    }

    private void draw() {
        if (viewModel == null || binding == null) {
            return;
        }
        int y = SafeUnboxUtil.safeUnbox(viewModel.translateY.getValue());
        List<Integer> notes = viewModel.notes.getValue();
        binding.editorBarsView.draw(y, notes);
    }
}
