package com.teamwss.websoso.data.remote.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostNovelRequest(
    @SerialName("userNovelRating")
    val userNovelRating: Float = 0f,
    @SerialName("userNovelReadEndDate")
    val userNovelReadEndDate: String = "",
    @SerialName("userNovelReadStartDate")
    val userNovelReadStartDate: String? = null,
    @SerialName("userNovelReadStatus")
    val userNovelReadStatus: String? = null,
)