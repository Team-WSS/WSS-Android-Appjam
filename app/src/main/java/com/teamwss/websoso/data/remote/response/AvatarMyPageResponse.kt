package com.teamwss.websoso.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvatarMyPageResponse(
    @SerialName("avatarId")
    val avatarId: Long,
    @SerialName("avatarImg")
    val avatarImg: String?,
    @SerialName("avatarCondition")
    val avatarCondition: String,
    @SerialName("avatarGenreBadgeImg")
    val avatarGenreBadgeImg: String,
    @SerialName("avatarMent")
    val avatarMent: String,
    @SerialName("avatarTag")
    val avatarTag: String,
    @SerialName("hasAvatar")
    val hasAvatar: Boolean,
)