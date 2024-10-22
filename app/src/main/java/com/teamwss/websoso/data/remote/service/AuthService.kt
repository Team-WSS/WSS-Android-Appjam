package com.teamwss.websoso.data.remote.service

import com.teamwss.websoso.data.remote.request.NicknamePatchRequest
import com.teamwss.websoso.data.remote.response.LoginResponse
import com.teamwss.websoso.data.remote.response.UserInfoMyPageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @POST("users/login")
    suspend fun postLogin(
        @Query("userId") userId: Long,
    ): LoginResponse

    @GET("users/info")
    suspend fun getMyPageUserInfo(
    ): UserInfoMyPageResponse

    @PATCH("users/nickname")
    suspend fun patchNickname(
        @Body request: NicknamePatchRequest,
    ): Response<Unit>
}