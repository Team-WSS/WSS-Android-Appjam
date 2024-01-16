package com.teamwss.websoso.data.remote.service

import com.teamwss.websoso.data.remote.response.AvatarHomeResponse
import retrofit2.http.GET

interface AvatarService {

    @GET("rep-avatar")
    suspend fun getRepresentativeAvatar(): AvatarHomeResponse

}