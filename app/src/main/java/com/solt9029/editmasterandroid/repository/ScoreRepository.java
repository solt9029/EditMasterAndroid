package com.solt9029.editmasterandroid.repository;

import com.solt9029.editmasterandroid.model.Score;
import com.solt9029.editmasterandroid.service.ScoreService;
import io.reactivex.Single;

import javax.inject.Inject;
import java.util.List;

public class ScoreRepository {
    private ScoreService service;

    @Inject
    public ScoreRepository(ScoreService service) {
        this.service = service;
    }

    public Single<List<Score>> getScoreTimeline(Integer count, String keyword, Integer maxId, Integer sinceId) {
        return service.getScoreTimeline(count, keyword, maxId, sinceId);
    }

    public Single<Score> getScore(Integer id) {
        return service.getScore(id);
    }
}
