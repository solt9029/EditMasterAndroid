package com.solt9029.editmasterandroid.entity

import com.google.gson.annotations.SerializedName

data class ScoreCreateValidationError(
        val message: String?,
        val errors: Errors?
)

data class Errors(
        val comment: List<String>?,
        val username: List<String>?,
        @field:SerializedName("video_id") val videoId: List<String>?,
        val bpm: List<String>?,
        val offset: List<String>?,
        val speed: List<String>?,
        val notes: List<String>?
)
