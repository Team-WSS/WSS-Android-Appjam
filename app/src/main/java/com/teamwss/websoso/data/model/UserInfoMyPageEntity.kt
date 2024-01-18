package com.teamwss.websoso.data.model

import com.teamwss.websoso.data.remote.response.UserAvatarResponse

data class UserInfoMyPageEntity(
    val memoCount: Long,
    val representativeAvatarGenreBadge: String,
    val representativeAvatarId: Long,
    val representativeAvatarLineContent: String,
    val representativeAvatarTag: String,
    val representativeAvatarImg: String,
    val userAvatars: List<UserAvatarResponse>,
    val userNickname: String,
    val userNovelCount: Long,
)