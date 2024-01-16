package com.teamwss.websoso.data.mapper

import com.teamwss.websoso.data.model.RepresentiveAvatarEntity
import com.teamwss.websoso.data.remote.response.AvatarHomeResponse

object AvatarHomeResponseMapper {

    fun AvatarHomeResponse.toData(): RepresentiveAvatarEntity {
        return RepresentiveAvatarEntity(
            avatarId = avatarId,
            avatarLine = avatarLine,
            avatarTag = avatarTag,
            userNickname = userNickname
        )
    }
}