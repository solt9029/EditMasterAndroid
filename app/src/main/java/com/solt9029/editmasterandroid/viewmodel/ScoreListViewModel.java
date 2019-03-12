package com.solt9029.editmasterandroid.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import com.solt9029.editmasterandroid.di.AppApplication;
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
    @Inject
    ScoreService service;
    @Inject
    CompositeDisposable compositeDisposable;

    ScoreListViewModel() {
        AppApplication.getApplication().getComponent().inject(this);

        isLoading.setValue(true);
        Disposable disposable = fetchScoreTimeline()
                .subscribe(
                        result -> {
                            isLoading.setValue(false);
                            scoreList.setValue(result);
                        },
                        throwable -> isLoading.setValue(false)
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void onCleared() {
        compositeDisposable.clear();
    }

    public void onRefresh() {
        if (isLoading.getValue() != null && isLoading.getValue()) {
            return;
        }

        isRefreshing.set(true);
        isLoading.setValue(true);
        Disposable disposable = fetchScoreTimeline()
                .subscribe(
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

    public void loadMore() {
        if (isLoading.getValue() != null && isLoading.getValue()) {
            return;
        }

        isLoading.setValue(true);
        Disposable disposable = fetchScoreTimeline(getMaxId())
                .subscribe(
                        result -> {
                            isLoading.setValue(false);
                            List<Score> newList = new ArrayList<>();
                            if (scoreList.getValue() != null) {
                                newList.addAll(scoreList.getValue());
                            }
                            newList.addAll(result);
                            scoreList.setValue(newList);
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
        return service.getScoreTimeline(null, maxId, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void select(int id) {
        selectedId.setValue(id);
    }
}