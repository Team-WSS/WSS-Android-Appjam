package com.teamwss.websoso.data.mapper

import com.teamwss.websoso.data.model.AvatarEntity
import com.teamwss.websoso.data.remote.response.AvatarResponse

object AvatarResponseMapper {

    fun AvatarResponse.toData() : AvatarEntity {
        return AvatarEntity(
            avatarCondition = avatarCondition,
            avatarGenreBadgeImg = avatarGenreBadgeImg,
            avatarMent = avatarMent,
            avatarTag = avatarTag
        )
    }
}