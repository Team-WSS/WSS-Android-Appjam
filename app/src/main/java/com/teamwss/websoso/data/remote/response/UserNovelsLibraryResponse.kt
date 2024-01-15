package com.teamwss.websoso.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserNovelsResponse(
    @SerialName("userNovelCount")
    val userNovelCount: Long,
    @SerialName("userNovels")
    val userNovels: List<UserNovel>
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
    @SerialName("userNovelRating")
    val userNovelRating: Float,
)