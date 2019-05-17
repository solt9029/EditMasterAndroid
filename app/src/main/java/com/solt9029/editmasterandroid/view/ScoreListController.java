package com.solt9029.editmasterandroid.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.Typed2EpoxyController;
import com.solt9029.editmasterandroid.ProgressBarItemBindingModel_;
import com.solt9029.editmasterandroid.ScoreItemBindingModel_;
import com.solt9029.editmasterandroid.model.Score;
import com.solt9029.editmasterandroid.viewmodel.Resource;

import java.util.List;

public class ScoreListController extends Typed2EpoxyController<Resource<List<Score>>, Boolean> {
    @AutoModel
    ProgressBarItemBindingModel_ progressBarItemBindingModel;
    private OnItemClickListener listener;

    public ScoreListController(OnItemClickListener listener) {
        super();
        this.listener = listener;
    }

    @Override
    public void buildModels(@Nullable Resource<List<Score>> resource, @NonNull Boolean isRefreshing) {
        if (resource == null) {
            return;
        }

        if (resource.getData() != null) {
            for (Score score : resource.getData()) {
                new ScoreItemBindingModel_().score(score).listener(listener).id(score.getId()).addTo(this);
            }
        }

        progressBarItemBindingModel.addIf(resource.isLoading() && !isRefreshing, this);

    }

    public interface OnItemClickListener {
        void onClick(Integer id);
    }
}