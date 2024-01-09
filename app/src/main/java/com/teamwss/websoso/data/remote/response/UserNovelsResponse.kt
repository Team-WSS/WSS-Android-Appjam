package com.teamwss.websoso.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserNovelsResponse(
    @SerialName("userNovelCount")
    val userNovelCount: Long,
    @SerialName("userNovelList")
    val userNovelList: List<UserNovel>
)

@Serializable
data class UserNovel(
    @SerialName("userNovelId")
    val userNovelId: Long,
    @SerialName("userNovelTitle")
    val userNovelTitle: String,
    @SerialName("userNovelImg")
    val userNovelImg: String,
    @SerialName("userNovelAuthor")
    val userNovelAuthor: String,
    @SerialName("userNovelGenre")
    val userNovelGenre: String,
    @SerialName("userNovelRating")
    val userNovelRating: Float
)