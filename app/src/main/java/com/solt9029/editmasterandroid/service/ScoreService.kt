package com.solt9029.editmasterandroid.service

import com.solt9029.editmasterandroid.entity.Score
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface ScoreService {
    @GET("scores/{id}")
    fun getScore(@Path("id") id: Int?): Single<Score>

    @GET("scores/timeline")
    fun getScoreTimeline(@Query("count") count: Int?, @Query("keyword") keyword: String?,
                         @Query("max_id") maxId: Int?, @Query("since_id") sinceId: Int?): Single<List<Score>>

    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("scores/create")
    fun createScore(@Body score: Score): Single<Response<Score>>

    companion object {
        const val BASE_URL = "http://editmasterapi.solt9029.com"
    }
}
