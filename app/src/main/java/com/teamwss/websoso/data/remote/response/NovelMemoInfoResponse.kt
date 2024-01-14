package com.teamwss.websoso.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NovelMemoInfoResponse(
    @SerialName("memos")
    val memos: List<NovelMemoResponse>,
    @SerialName("platforms")
    val platforms: List<PlatformGetResponse>,
    @SerialName("userNovelGenre")
    val userNovelGenre: String,
    @SerialName("userNovelRating")
    val userNovelRating: Float,
    @SerialName("userNovelReadEndDate")
    val userNovelReadEndDate: String,
    @SerialName("userNovelReadStartDate")
    val userNovelReadStartDate: String,
    @SerialName("userNovelReadStatus")
    val userNovelReadStatus: String,
    @SerialName("userNovelDescription")
    val userNovelDescription: String,
)