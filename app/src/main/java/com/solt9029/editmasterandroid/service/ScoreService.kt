package com.solt9029.editmasterandroid.service

import com.solt9029.editmasterandroid.model.Score
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ScoreService {
    @GET("scores/{id}")
    fun getScore(@Path("id") id: Int?): Single<Score>

    @GET("scores/timeline")
    fun getScoreTimeline(@Query("count") count: Int?, @Query("keyword") keyword: String,
                         @Query("max_id") maxId: Int?, @Query("since_id") sinceId: Int?): Single<List<Score>>

    companion object {
        const val BASE_URL = "http://editmasterapi.solt9029.com"
    }
}