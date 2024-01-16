package com.teamwss.websoso.data.repository

import com.teamwss.websoso.data.mapper.AvatarHomeResponseMapper.toData
import com.teamwss.websoso.data.model.RepresentiveAvatarEntity
import com.teamwss.websoso.data.remote.service.AvatarService

class AvatarRepository(
    private val avatarService: AvatarService
) {

    suspend fun getRepresentativeAvatar(): RepresentiveAvatarEntity =
        avatarService.getRepresentativeAvatar().toData()
}