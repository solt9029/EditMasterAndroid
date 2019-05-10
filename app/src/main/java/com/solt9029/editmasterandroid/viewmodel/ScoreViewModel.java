package com.solt9029.editmasterandroid.viewmodel;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.mlykotom.valifi.fields.ValiFieldText;
import com.solt9029.editmasterandroid.R;
import com.solt9029.editmasterandroid.model.Score;
import com.solt9029.editmasterandroid.repository.ScoreRepository;
import com.solt9029.editmasterandroid.view.customview.ScrollContainerView;

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
import timber.log.Timber;

public class ScoreViewModel extends ViewModel {
    public UnitLiveEvent navigateToScoreSettingsFragment = new UnitLiveEvent();
    public ValiFieldText username = new ValiFieldText("通りすがりの創作の達人");
    public MutableLiveData<Field<String>> videoId = new MutableLiveData<>(new Field<>("jhOVibLEDhA"));
    public ValiFieldFloat bpm = new ValiFieldFloat(158f);
    public ValiFieldFloat offset = new ValiFieldFloat(0.75f);
    public ValiFieldFloat speed = new ValiFieldFloat(1f);
    public ValiFieldText comment = new ValiFieldText("創作の達人で創作譜面をしました！");
    public MutableLiveData<List<Integer>> notes = new MutableLiveData<>(new ArrayList<>(Arrays.asList(new Integer[192])));
    public MutableLiveData<List<Integer>> states = new MutableLiveData<>(new ArrayList<>(Arrays.asList(new Integer[192])));
    public MutableLiveData<Integer> translateY = new MutableLiveData<>(0);

    public ScrollContainerView.OnScrollChangeListener onScrollChange = (x, y, oldX, oldY) -> translateY.setValue(y);
    public View.OnTouchListener onTouch = (view, event) -> {
        view.performClick();
        float x = event.getX();
        float y = event.getY();
        Timber.d("x" + x + ",y" + y);
        return false;
    };

    public Context context;
    private Thread thread;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ScoreRepository repository;

    @Inject
    ScoreViewModel(ScoreRepository repository, Context context) {
        this.repository = repository;
        this.context = context;

        thread = new Thread(() -> {
            while (thread != null) {
                Timber.d("currentTimeMillis: " + System.currentTimeMillis());
            }
        });
        thread.start();

        Resources resources = context.getResources();
        username.addMaxLengthValidator(resources.getString(R.string.max_length_validation_message, 20), 20);
        username.addNotEmptyValidator(resources.getString(R.string.not_empty_validation_message));
        bpm.addNotEmptyValidator(resources.getString(R.string.not_empty_validation_message));
        offset.addNotEmptyValidator(resources.getString(R.string.not_empty_validation_message));
        speed.addNotEmptyValidator(resources.getString(R.string.not_empty_validation_message));
        comment.addMaxLengthValidator(resources.getString(R.string.max_length_validation_message, 140), 140);
    }

    public void onVideoIdChange(CharSequence sequence, int start, int before, int count) {
        Field<String> videoIdField = new Field<>(sequence.toString());
        if (sequence.length() == 0) {
            videoIdField.setError(context.getResources().getString(R.string.not_empty_validation_message));
        }
        videoId.setValue(videoIdField);
    }

    @Override
    public void onCleared() {
        compositeDisposable.clear();
        thread = null;
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
                    videoId.setValue(new Field<>(result.getVideoId()));
                    bpm.setValue(result.getBpm().toString());
                    offset.setValue(result.getOffset().toString());
                    speed.setValue(result.getSpeed().toString());
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