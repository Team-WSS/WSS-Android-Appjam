package com.teamwss.websoso.data.repository

import com.teamwss.websoso.data.remote.response.AvatarHomeResponse
import com.teamwss.websoso.data.remote.service.AvatarService

class AvatarRepository(
    private val avatarService: AvatarService
) {

    suspend fun getRepresentativeAvatar(): AvatarHomeResponse =
        avatarService.getRepresentativeAvatar()
}