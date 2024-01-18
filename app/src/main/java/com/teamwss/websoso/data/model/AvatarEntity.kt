package com.teamwss.websoso.data.model

data class AvatarEntity(
    val avatarId: Long,
    val avatarImg: String,
    val avatarCondition: String,
    val avatarGenreBadgeImg: String,
    val avatarMent: String,
    val avatarTag: String,
    val hasAvatar: Boolean,
)