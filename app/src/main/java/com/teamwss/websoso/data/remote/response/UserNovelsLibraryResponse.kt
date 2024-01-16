package com.teamwss.websoso.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserNovelsLibraryResponse(
    @SerialName("userNovelCount")
    val userNovelCount: Long,
    @SerialName("userNovels")
    val userNovelResponses: List<UserNovelResponse>
)

@Serializable
data class UserNovelResponse(
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