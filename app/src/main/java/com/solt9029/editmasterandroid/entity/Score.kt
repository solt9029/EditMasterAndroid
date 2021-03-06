package com.solt9029.editmasterandroid.entity

import com.google.gson.annotations.SerializedName

data class Score(
        val id: Int? = null,
        val username: String?,
        val comment: String?,
        @field:SerializedName("video_id") val videoId: String?,
        val bpm: Float?,
        val offset: Float?,
        val speed: Float?,
        val notes: MutableList<Int>?,
        @field:SerializedName("advanced_settings") val advancedSettings: Any? = null,
        @field:SerializedName("created_at") val createdAt: String? = null,
        @field:SerializedName("updated_at") val updatedAt: String? = null)