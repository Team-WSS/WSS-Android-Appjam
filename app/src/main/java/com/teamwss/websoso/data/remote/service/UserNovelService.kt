package com.teamwss.websoso.data.remote.service

import com.teamwss.websoso.data.remote.request.NovelPostRequest
import com.teamwss.websoso.data.remote.response.UserNovelPostResponse
import com.teamwss.websoso.data.remote.response.UserNovelsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface UserNovelService {

    @GET("user-novels")
    suspend fun getNovels(
        @Query("readStatus") readStatus: String,
        @Query("lastUserNovelId") lastUserNovelId: Long,
        @Query("size") size: Int,
        @Query("sortType") sortType: String
    ): UserNovelsResponse

    @GET("novels/{novelId}")
    suspend fun getEditNovelInfo(
        @Path("novelId") novelId: Long
    ): UserNovelPostResponse

    @PATCH("user-novels/{userNovelId}")
    suspend fun patchPostNovelInfo(
        @Path("userNovelId") userNovelId: Long,
        @Body request: NovelPostRequest,
    ): Response<Unit>
}