package com.teamwss.websoso.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostNovelResponse(
    @SerialName("novelAuthor")
    val novelAuthor: String = "",
    @SerialName("novelDescription")
    val novelDescription: String = "",
    @SerialName("novelGenre")
    val novelGenre: String = "",
    @SerialName("novelId")
    val novelId: Long = 0,
    @SerialName("novelImg")
    val novelImg: String = "",
    @SerialName("novelTitle")
    val novelTitle: String = "",
    @SerialName("platformList")
    val platformList: List<PlatformGetResponse> = listOf()
)