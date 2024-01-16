package com.teamwss.websoso.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserAvatarResponse(
    @SerialName("avatarId")
    val avatarId: Long,
    @SerialName("avatarImg")
    val avatarImg: String,
    @SerialName("hasAvatar")
    val hasAvatar: Boolean
)