package com.teamwss.websoso.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlatformGetResponse(
    @SerialName("platformId")
    val platformId: Int = 0,
    @SerialName("platformName")
    val platformName: String = "",
    @SerialName("platformUrl")
    val platformUrl: String = ""
)