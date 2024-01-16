package com.teamwss.websoso.data.remote.service

import com.teamwss.websoso.data.remote.request.NicknamePatchRequest
import com.teamwss.websoso.data.remote.request.NovelPostRequest
import com.teamwss.websoso.data.remote.response.MyPageUserInfoResponse
import com.teamwss.websoso.data.remote.response.UserNovelsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {

    @POST("users/login")
    suspend fun postLogin(
    ): UserNovelsResponse

    @GET("users/info")
    suspend fun getMyPageUserInfo(
    ): MyPageUserInfoResponse

    @PATCH("users/nickname")
    suspend fun patchNickname(
        @Body request: NicknamePatchRequest,
    ): Response<Unit>
}