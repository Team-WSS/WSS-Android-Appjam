package com.teamwss.websoso.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvatarResponse(
    @SerialName("avatarCondition")
    val avatarCondition: String,
    @SerialName("avatarGenreBadgeImg")
    val avatarGenreBadgeImg: String,
    @SerialName("avatarMent")
    val avatarMent: String,
    @SerialName("avatarTag")
    val avatarTag: String
)