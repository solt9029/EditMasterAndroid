package com.solt9029.editmasterandroid.response

import com.google.gson.annotations.SerializedName

data class Score(
        val id: Int?,
        val username: String?,
        val comment: String?,
        @field:SerializedName("video_id") val videoId: String?,
        val bpm: Float?,
        val offset: Float?,
        val speed: Float?,
        val notes: List<Int>?,
        @field:SerializedName("advanced_settings") val advancedSettings: Any?,
        @field:SerializedName("created_at") val createdAt: String?,
        @field:SerializedName("updated_at") val updatedAt: String?)