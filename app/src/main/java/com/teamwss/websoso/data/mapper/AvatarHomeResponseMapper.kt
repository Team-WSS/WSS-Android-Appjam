package com.teamwss.websoso.data.mapper

import com.teamwss.websoso.data.model.RepresentiveAvatarEntity
import com.teamwss.websoso.data.remote.response.AvatarHomeResponse

object AvatarHomeResponseMapper {

    fun AvatarHomeResponse.toData(): RepresentiveAvatarEntity {
        return RepresentiveAvatarEntity(
            avatarId = this.avatarId,
            avatarLine = this.avatarLine,
            avatarTag = this.avatarTag,
            userNickname = this.userNickname
        )
    }
}