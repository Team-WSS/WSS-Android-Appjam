package com.teamwss.websoso.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoMyPageResponse(
    @SerialName("memoCount")
    val memoCount: Long,
    @SerialName("representativeAvatarGenreBadge")
    val representativeAvatarGenreBadge: String,
    @SerialName("representativeAvatarId")
    val representativeAvatarId: Long,
    @SerialName("representativeAvatarLineContent")
    val representativeAvatarLineContent: String,
    @SerialName("representativeAvatarTag")
    val representativeAvatarTag: String,
    @SerialName("representativeAvatarImg")
    val representativeAvatarImg: String,
    @SerialName("userAvatars")
    val userAvatars: List<UserAvatarResponse>,
    @SerialName("userNickname")
    val userNickname: String,
    @SerialName("userNovelCount")
    val userNovelCount: Long
)