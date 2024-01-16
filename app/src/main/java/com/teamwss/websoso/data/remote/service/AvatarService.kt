package com.teamwss.websoso.data.remote.service

import com.teamwss.websoso.data.remote.response.AvatarHomeResponse
import retrofit2.http.GET

import com.teamwss.websoso.data.remote.response.AvatarResponse
import retrofit2.http.Path

interface AvatarService {
    suspend fun getRepresentativeAvatar(): AvatarHomeResponse
    @GET("/avatars/{avatarId}")
    suspend fun getAvatarInfo(
        @Path("avatarId") avatarId: Number
    ): AvatarResponse

}