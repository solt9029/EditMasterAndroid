package com.solt9029.editmasterandroid.model;

import java.util.List;

public class ScoreListResource {
    public List<Score> data = null;
    public boolean isLoading = false;
    public Throwable error = null;

    public ScoreListResource() {

    }

    private ScoreListResource(List<Score> data, boolean isLoading, Throwable error) {
        this.data = data;
        this.isLoading = isLoading;
        this.error = error;
    }

    public static ScoreListResource startLoading(List<Score> data) {
        return new ScoreListResource(data, true, null);
    }

    public static ScoreListResource finishLoadingSuccess(List<Score> data) {
        return new ScoreListResource(data, false, null);
    }

    public static ScoreListResource finishLoadingFailure(Throwable error) {
        return new ScoreListResource(null, false, error);
    }
}
