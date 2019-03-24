package com.solt9029.editmasterandroid.view;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.Typed3EpoxyController;
import com.solt9029.editmasterandroid.ProgressBarItemBindingModel_;
import com.solt9029.editmasterandroid.ScoreItemBindingModel_;
import com.solt9029.editmasterandroid.model.Score;

import java.util.List;

public class ScoreListController extends Typed3EpoxyController<List<Score>, Boolean, Boolean> {
    @AutoModel
    ProgressBarItemBindingModel_ progressBarItemBindingModel;
    private ScoreItemClickCallback callback;

    ScoreListController(ScoreItemClickCallback callback) {
        super();
        this.callback = callback;
    }

    @Override
    public void buildModels(List<Score> list, Boolean isLoading, Boolean isRefreshing) {
        if (list != null) {
            for (Score score : list) {
                new ScoreItemBindingModel_().score(score).callback(callback).id(score.getId()).addTo(this);
            }
        }

        if (isLoading != null && isRefreshing != null) {
            progressBarItemBindingModel.addIf(isLoading && !isRefreshing, this);
        }
    }
}