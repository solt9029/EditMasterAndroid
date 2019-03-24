package com.solt9029.editmasterandroid.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import com.solt9029.editmasterandroid.model.Score;
import com.solt9029.editmasterandroid.service.ScoreService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ScoreListViewModel extends ViewModel {
    public MutableLiveData<List<Score>> scoreList = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public ObservableBoolean isRefreshing = new ObservableBoolean(false);
    public MutableLiveData<Integer> selectedId = new MutableLiveData<>();
    public MutableLiveData<String> keyword = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ScoreService service;


    @Inject
    ScoreListViewModel(ScoreService service) {
        this.service = service;

        initScoreList();
    }

    @Override
    public void onCleared() {
        compositeDisposable.clear();
    }

    public void onRefresh() {
        compositeDisposable.clear();

        isRefreshing.set(true);
        isLoading.setValue(true);
        Disposable disposable = fetchScoreTimeline().subscribe(
                result -> {
                    isRefreshing.set(false);
                    isLoading.setValue(false);
                    scoreList.setValue(result);
                },
                throwable -> {
                    isRefreshing.set(false);
                    isLoading.setValue(false);
                }
        );
        compositeDisposable.add(disposable);
    }

    public void onLoadMore() {
        if (isLoading.getValue() != null && isLoading.getValue()) {
            return;
        }

        Integer maxId = getMaxId();
        if (maxId != null && maxId < 1) {
            return;
        }

        isLoading.setValue(true);
        Disposable disposable = fetchScoreTimeline(maxId).subscribe(
                result -> {
                    isLoading.setValue(false);
                    addScoreList(result);
                },
                throwable -> isLoading.setValue(false)
        );
        compositeDisposable.add(disposable);
    }

    public void initScoreList() {
        compositeDisposable.clear();

        isLoading.setValue(true);
        scoreList.setValue(null);
        Disposable disposable = fetchScoreTimeline().subscribe(
                result -> {
                    isLoading.setValue(false);
                    scoreList.setValue(result);
                },
                throwable -> isLoading.setValue(false)
        );
        compositeDisposable.add(disposable);
    }

    private Integer getMaxId() {
        Integer maxId = null;
        List<Score> list = scoreList.getValue();
        if (list != null) {
            maxId = list.get(list.size() - 1).getId() - 1;
        }
        return maxId;
    }

    private Single<List<Score>> fetchScoreTimeline() {
        return fetchScoreTimeline(null);
    }

    private Single<List<Score>> fetchScoreTimeline(Integer maxId) {
        return service.getScoreTimeline(null, keyword.getValue(), maxId, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void addScoreList(List<Score> result) {
        List<Score> newList = new ArrayList<>();
        if (scoreList.getValue() != null) {
            newList.addAll(scoreList.getValue());
        }
        newList.addAll(result);
        scoreList.setValue(newList);
    }

    public void select(int id) {
        selectedId.setValue(id);
    }
}