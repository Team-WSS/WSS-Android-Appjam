package com.teamwss.websoso.data.mapper

import com.teamwss.websoso.data.model.AvatarEntity
import com.teamwss.websoso.data.remote.response.AvatarMyPageResponse

object AvatarMyPageResponseMapper {

    fun AvatarMyPageResponse.toData(): AvatarEntity {
        return AvatarEntity(
            avatarId = avatarId,
            avatarCondition = avatarCondition,
            avatarGenreBadgeImg = avatarGenreBadgeImg,
            avatarMent = avatarMent,
            avatarTag = avatarTag,
            hasAvatar = hasAvatar
        )
    }
}