package com.teamwss.websoso.data.remote.service

import com.teamwss.websoso.data.remote.response.UserNovelsLibraryResponse
import retrofit2.http.POST

interface AuthService {

    @POST("users/login")
    suspend fun postLogin(
    ): UserNovelsLibraryResponse
}