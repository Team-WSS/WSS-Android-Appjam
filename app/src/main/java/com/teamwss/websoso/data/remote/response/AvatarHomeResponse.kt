package com.teamwss.websoso.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvatarHomeResponse(
    @SerialName("avatarId")
    val avatarId: Long,
    @SerialName("avatarLine")
    val avatarLine: String,
    @SerialName("avatarTag")
    val avatarTag: String,
    @SerialName("userNickname")
    val userNickname: String
)