package com.teamwss.websoso.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NovelPostRequest(
    @SerialName("userNovelRating")
    val userNovelRating: Float?,
    @SerialName("userNovelReadStatus")
    val userNovelReadStatus: String,
    @SerialName("userNovelReadStartDate")
    val userNovelReadStartDate: String?,
    @SerialName("userNovelReadEndDate")
    val userNovelReadEndDate: String?,
)