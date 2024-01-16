package com.teamwss.websoso.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SosopickNovelHomeResponse(
    @SerialName("sosoPickNovels")
    val sosoPickNovelResponses: List<SosoPickNovelResponse>
)

@Serializable
data class SosoPickNovelResponse(
    @SerialName("novelAuthor")
    val novelAuthor: String,
    @SerialName("novelImg")
    val novelImg: String,
    @SerialName("novelRegisteredCount")
    val novelRegisteredCount: Int,
    @SerialName("novelTitle")
    val novelTitle: String
)