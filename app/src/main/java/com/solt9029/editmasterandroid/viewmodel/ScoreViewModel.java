package com.solt9029.editmasterandroid.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.databinding.ObservableField;

import com.solt9029.editmasterandroid.model.Score;
import com.solt9029.editmasterandroid.service.ScoreService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ScoreViewModel extends ViewModel {
    private ScoreService service;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ObservableField<Score> score = new ObservableField<>();

    @Inject
    ScoreViewModel(ScoreService service) {
        this.service = service;
    }

    @Override
    public void onCleared() {
        compositeDisposable.clear();
    }

    public void initScore(int id) {
        // new
        if (id < 1) {
            score.set(getDefaultScore());
            return;
        }

        // show
        Disposable disposable = fetchScore(id).subscribe(
                result -> {
                    result.initStates();
                    score.set(result);
                },
                throwable -> {
                }
        );
        compositeDisposable.add(disposable);
    }

    private Single<Score> fetchScore(int id) {
        return service.getScore(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private Score getDefaultScore() {
        String username = "通りすがりの創作の達人";
        String comment = "創作の達人で創作譜面をしました！";
        String videoId = "jhOVibLEDhA";
        Float bpm = 158f;
        Float offset = 0.75f;
        Float speed = 1f;
        List<Integer> notes = new ArrayList<>(Arrays.asList(0, 0));

        return new Score(username, comment, videoId, bpm, offset, speed, notes);
    }
}
