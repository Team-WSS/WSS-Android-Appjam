package com.teamwss.websoso.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchNovelsResponse(
    @SerialName("novels")
    val novels: List<Novel>
) {
    @Serializable
    data class Novel(
        @SerialName("novelId")
        val novelId: Long,
        @SerialName("novelImg")
        val novelImg: String,
        @SerialName("novelTitle")
        val novelTitle: String,
        @SerialName("novelAuthor")
        val novelAuthor: String,
        @SerialName("novelGenre")
        val novelGenre: String,
    )
}