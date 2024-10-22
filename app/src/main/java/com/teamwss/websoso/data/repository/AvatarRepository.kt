package com.teamwss.websoso.data.repository

import com.teamwss.websoso.data.mapper.AvatarHomeResponseMapper.toData
import com.teamwss.websoso.data.mapper.AvatarMyPageResponseMapper.toData
import com.teamwss.websoso.data.model.AvatarEntity
import com.teamwss.websoso.data.model.RepresentiveAvatarEntity
import com.teamwss.websoso.data.remote.request.RepresentativeAvatarPatchRequest
import com.teamwss.websoso.data.remote.service.AvatarService

class AvatarRepository(
    private val avatarService: AvatarService
) {

    suspend fun getRepresentativeAvatar(): RepresentiveAvatarEntity =
        avatarService.getRepresentativeAvatar().toData()

    suspend fun getAvatarInfo(avatarId: Long): AvatarEntity =
        avatarService.getAvatarInfo(avatarId).toData()

    suspend fun patchRepresentativeAvatar(avatarId: Long) =
        avatarService.patchRepresentativeAvatar(RepresentativeAvatarPatchRequest(avatarId))
}