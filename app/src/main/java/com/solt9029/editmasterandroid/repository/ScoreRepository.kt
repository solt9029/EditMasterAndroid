package com.solt9029.editmasterandroid.repository

import com.solt9029.editmasterandroid.entity.Score
import com.solt9029.editmasterandroid.service.ScoreService
import io.reactivex.Single
import retrofit2.Response

import javax.inject.Inject

class ScoreRepository @Inject constructor(private val service: ScoreService) {
    fun getScoreTimeline(count: Int?, keyword: String?, maxId: Int?, sinceId: Int?): Single<List<Score>> {
        return service.getScoreTimeline(count, keyword, maxId, sinceId)
    }

    fun getScore(id: Int?): Single<Score> {
        return service.getScore(id)
    }

    fun createScore(score: Score): Single<Response<Score>> {
        return service.createScore(score)
    }
}
