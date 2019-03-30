package com.solt9029.editmasterandroid.viewmodel;

import com.solt9029.editmasterandroid.model.Resource;
import com.solt9029.editmasterandroid.model.Score;
import com.solt9029.editmasterandroid.service.ScoreService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ScoreListViewModel extends ViewModel {
    public MutableLiveData<Resource<List<Score>>> resource = new MutableLiveData<>(new Resource<>());
    public ObservableBoolean isRefreshing = new ObservableBoolean(false);
    public MutableLiveData<Integer> selectedId = new MutableLiveData<>();
    public MutableLiveData<String> keyword = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ScoreService service;

    @Inject
    ScoreListViewModel(ScoreService service) {
        this.service = service;
        onLoad();
    }

    @Override
    public void onCleared() {
        compositeDisposable.clear();
    }

    public void onRefresh() {
        compositeDisposable.clear();

        isRefreshing.set(true);
        resource.setValue(Resource.startLoading(getData()));
        Disposable disposable = fetchScoreTimeline().subscribe(
                result -> {
                    isRefreshing.set(false);
                    resource.setValue(Resource.finishLoadingSuccess(result));
                },
                error -> resource.setValue(Resource.finishLoadingFailure(error))
        );
        compositeDisposable.add(disposable);
    }

    public void onLoad() {
        compositeDisposable.clear();

        resource.setValue(Resource.startLoading(null));
        Disposable disposable = fetchScoreTimeline().subscribe(
                result -> resource.setValue(Resource.finishLoadingSuccess(result)),
                error -> resource.setValue(Resource.finishLoadingFailure(error))
        );
        compositeDisposable.add(disposable);
    }

    public void onLoadMore() {
        if (getIsLoading()) {
            return;
        }

        Integer maxId = getMaxId();
        if (maxId != null && maxId < 1) {
            return;
        }

        resource.setValue(Resource.startLoading(getData()));
        Disposable disposable = fetchScoreTimeline(maxId).subscribe(
                result -> resource.setValue(Resource.finishLoadingSuccess(addData(result))),
                error -> resource.setValue(Resource.finishLoadingFailure(error))
        );
        compositeDisposable.add(disposable);
    }

    private Integer getMaxId() {
        Integer maxId = null;
        List<Score> data = getData();
        if (data != null) {
            maxId = data.get(data.size() - 1).getId() - 1;
        }
        return maxId;
    }

    @Nullable
    public List<Score> getData() {
        List<Score> data = null;
        if (resource.getValue() != null) {
            data = resource.getValue().data;
        }
        return data;
    }

    private boolean getIsLoading() {
        boolean isLoading = true;
        if (resource.getValue() != null) {
            isLoading = resource.getValue().isLoading;
        }
        return isLoading;
    }

    private Single<List<Score>> fetchScoreTimeline() {
        return fetchScoreTimeline(null);
    }

    private Single<List<Score>> fetchScoreTimeline(Integer maxId) {
        return service.getScoreTimeline(null, keyword.getValue(), maxId, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private List<Score> addData(List<Score> result) {
        List<Score> newData = new ArrayList<>();
        if (getData() != null) {
            newData.addAll(getData());
        }
        newData.addAll(result);
        return newData;
    }

    public void select(int id) {
        selectedId.setValue(id);
    }
}