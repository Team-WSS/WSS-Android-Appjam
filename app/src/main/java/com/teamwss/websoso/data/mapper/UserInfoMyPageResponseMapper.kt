package com.teamwss.websoso.data.mapper

import com.teamwss.websoso.data.model.UserInfoMyPageEntity
import com.teamwss.websoso.data.remote.response.UserInfoMyPageResponse

object UserInfoMyPageResponseMapper {

    fun UserInfoMyPageResponse.toData(): UserInfoMyPageEntity {
        return UserInfoMyPageEntity(
            memoCount = memoCount,
            representativeAvatarGenreBadge = representativeAvatarGenreBadge,
            representativeAvatarId = representativeAvatarId,
            representativeAvatarLineContent = representativeAvatarLineContent,
            representativeAvatarTag = representativeAvatarTag,
            representativeAvatarImg = representativeAvatarImg,
            userAvatars = userAvatars,
            userNickname = userNickname,
            userNovelCount = userNovelCount,
        )
    }
}