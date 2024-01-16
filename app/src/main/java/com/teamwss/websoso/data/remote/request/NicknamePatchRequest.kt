package com.teamwss.websoso.data.remote.request

import androidx.lifecycle.LiveData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NicknamePatchRequest(
    @SerialName("userNickname")
    val userNickname: String,
)
