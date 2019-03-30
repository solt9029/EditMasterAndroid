package com.solt9029.editmasterandroid.view;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.Typed2EpoxyController;
import com.airbnb.epoxy.TypedEpoxyController;
import com.solt9029.editmasterandroid.ProgressBarItemBindingModel_;
import com.solt9029.editmasterandroid.ScoreItemBindingModel_;
import com.solt9029.editmasterandroid.model.Score;
import com.solt9029.editmasterandroid.model.ScoreListResource;

import androidx.annotation.NonNull;

public class ScoreListController extends Typed2EpoxyController<ScoreListResource, Boolean> {
    @AutoModel
    ProgressBarItemBindingModel_ progressBarItemBindingModel;
    private ScoreItemClickCallback callback;

    ScoreListController(ScoreItemClickCallback callback) {
        super();
        this.callback = callback;
    }

    @Override
    public void buildModels(ScoreListResource resource, @NonNull Boolean isRefreshing) {
        if (resource == null) {
            return;
        }

        if (resource.data != null) {
            for (Score score : resource.data) {
                new ScoreItemBindingModel_().score(score).callback(callback).id(score.getId()).addTo(this);
            }
        }

        progressBarItemBindingModel.addIf(resource.isLoading && !isRefreshing, this);

    }
}