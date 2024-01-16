package com.teamwss.websoso.data.mapper

import com.teamwss.websoso.data.model.UserInfoMyPageEntity
import com.teamwss.websoso.data.remote.response.UserInfoMyPageResponse

object UserInfoMyPageResponseMapper {

    fun List<UserInfoMyPageResponse>.toData(): List<UserInfoMyPageEntity> {
        return this.map {
            UserInfoMyPageEntity(
                memoCount = it.memoCount,
                representativeAvatarGenreBadge = it.representativeAvatarGenreBadge,
                representativeAvatarId = it.representativeAvatarId,
                representativeAvatarLineContent = it.representativeAvatarLineContent,
                representativeAvatarTag = it.representativeAvatarTag,
                representativeAvatarImg = it.representativeAvatarImg,
                userAvatars = it.userAvatars,
                userNickName = it.userNickName,
                userNovelCount = it.userNovelCount
            )
        }
    }
}