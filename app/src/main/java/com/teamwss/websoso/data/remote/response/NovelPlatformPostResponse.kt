package com.teamwss.websoso.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NovelPlatformPostResponse(
    @SerialName("platformName")
    val platformName: String,
    @SerialName("platformUrl")
    val platformUrl: String,
)