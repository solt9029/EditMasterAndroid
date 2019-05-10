package com.solt9029.editmasterandroid.viewmodel;

import com.mlykotom.valifi.fields.ValiFieldText;
import com.solt9029.editmasterandroid.model.Score;
import com.solt9029.editmasterandroid.repository.ScoreRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ScoreViewModel extends ViewModel {
    public UnitLiveEvent navigateToScoreSettingsFragment = new UnitLiveEvent();
    public ValiFieldText username = new ValiFieldText("通りすがりの創作の達人");
    public MutableLiveData<String> comment = new MutableLiveData<>("創作の達人で創作譜面をしました！");
    public MutableLiveData<String> videoId = new MutableLiveData<>("jhOVibLEDhA");
    public ValiFieldFloat bpm = new ValiFieldFloat(158f);
    public MutableLiveData<Float> offset = new MutableLiveData<>(0.75f);
    public MutableLiveData<Float> speed = new MutableLiveData<>(1f);
    Integer[] array = new Integer[192];
    public MutableLiveData<List<Integer>> notes = new MutableLiveData<>(new ArrayList<>(Arrays.asList(array)));
    public MutableLiveData<List<Integer>> states = new MutableLiveData<>(new ArrayList<>(Arrays.asList(array)));
    public MutableLiveData<Integer> translateY = new MutableLiveData<>(0);

    private ScoreRepository repository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    ScoreViewModel(ScoreRepository repository) {
        this.repository = repository;
        username.addMaxLengthValidator("20文字以内の文字列を指定してください。", 20);
        username.addNotEmptyValidator();
    }

    @Override
    public void onCleared() {
        compositeDisposable.clear();
    }

    public void initScore(int id) {
        // new
        if (id < 1) {
            return;
        }

        // show
        Disposable disposable = fetchScore(id).subscribe(
                result -> {
                    username.setValue(result.getUsername());
                    comment.setValue(result.getComment());
                    videoId.setValue(result.getVideoId());
                    bpm.setValue(result.getBpm().toString());
                    offset.setValue(result.getOffset());
                    speed.setValue(result.getSpeed());
                    notes.setValue(result.getNotes());
                    states.setValue(new ArrayList<>(Collections.nCopies(notes.getValue().size(), 0)));
                },
                throwable -> {
                }
        );
        compositeDisposable.add(disposable);
    }

    private Single<Score> fetchScore(int id) {
        return repository.getScore(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public void navigateToScoreSettingsFragment() {
        navigateToScoreSettingsFragment.call();
    }
}
