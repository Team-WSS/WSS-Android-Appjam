package com.teamwss.websoso.data.remote.service

import com.teamwss.websoso.data.remote.request.LoginRequest
import com.teamwss.websoso.data.remote.response.LoginResponse
import com.teamwss.websoso.data.remote.response.UserNovelsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LibraryService {

    @POST("users/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("user-novels")
    suspend fun getNovels(
        @Query("readStatus") readStatus: String,
        @Query("lastUserNovelId") lastUserNovelId: Long,
        @Query("size") size: Int,
        @Query("sortType") sortType: String
    ): UserNovelsResponse

}