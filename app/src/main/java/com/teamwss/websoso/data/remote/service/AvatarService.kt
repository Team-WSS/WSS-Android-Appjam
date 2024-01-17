package com.teamwss.websoso.data.remote.service

import com.teamwss.websoso.data.remote.request.RepresentativeAvatarPatchRequest
import com.teamwss.websoso.data.remote.response.AvatarHomeResponse
import retrofit2.http.GET
import com.teamwss.websoso.data.remote.response.AvatarResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.Path

interface AvatarService {

    @GET("rep-avatar")
    suspend fun getRepresentativeAvatar(): AvatarHomeResponse

    @PATCH("rep-avatar")
    suspend fun patchRepresentativeAvatar(
        @Body request: RepresentativeAvatarPatchRequest
    ) : Response<Unit>

    @GET("avatars/{avatarId}")
    suspend fun getAvatarInfo(
        @Path("avatarId") avatarId: Long
    ): AvatarResponse


}