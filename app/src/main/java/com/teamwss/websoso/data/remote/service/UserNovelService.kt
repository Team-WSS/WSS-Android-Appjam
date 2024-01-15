package com.teamwss.websoso.data.remote.service

import com.teamwss.websoso.data.remote.request.NovelPostRequest
import com.teamwss.websoso.data.remote.response.UserNovelPostResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface UserNovelService {

    @GET("user-novels")
    suspend fun getNovels(
        @Query("readStatus") readStatus: String,
        @Query("lastUserNovelId") lastUserNovelId: Long,
        @Query("size") size: Int,
        @Query("sortType") sortType: String
    ): UserNovelsResponse


    @GET("/novels/{novelId}")
    suspend fun fetchEditNovelInfo(
        @Path("novelId") novelId: Long
    ): UserNovelPostResponse

    @PATCH("/user-novels/{userNovelId}")
    suspend fun editPostNovelInfo(
        @Path("userNovelId") userNovelId: Long,
        @Body request: NovelPostRequest,
    ): Response<Unit>
}