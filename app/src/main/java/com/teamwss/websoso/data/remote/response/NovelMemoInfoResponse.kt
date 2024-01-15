package com.teamwss.websoso.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NovelMemoInfoResponse(
    @SerialName("memos")
    val memos: List<NovelMemoResponse>,
    @SerialName("platforms")
    val platforms: List<NovelPlatformsInfoResponse>,
    @SerialName("userNovelGenreBadgeImg")
    val userNovelGenreBadgeImg: String,
    @SerialName("userNovelGenre")
    val userNovelGenre: String,
    @SerialName("userNovelRating")
    val userNovelRating: Float,
    @SerialName("userNovelReadEndDate")
    val userNovelReadEndDate: String?,
    @SerialName("userNovelReadStartDate")
    val userNovelReadStartDate: String?,
    @SerialName("userNovelReadStatus")
    val userNovelReadStatus: String,
    @SerialName("userNovelDescription")
    val userNovelDescription: String,
    @SerialName("userNovelTitle")
    val userNovelTitle: String,
    @SerialName("userNovelImg")
    val userNovelImg: String,
    @SerialName("userNovelAuthor")
    val userNovelAuthor: String,
)