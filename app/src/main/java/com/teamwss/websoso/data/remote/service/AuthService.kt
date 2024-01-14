package com.teamwss.websoso.data.remote.service

import com.teamwss.websoso.data.remote.response.UserNovelsResponse
import retrofit2.http.POST

interface AuthService {

    @POST("users/login")
    suspend fun login(
    ): UserNovelsResponse
}