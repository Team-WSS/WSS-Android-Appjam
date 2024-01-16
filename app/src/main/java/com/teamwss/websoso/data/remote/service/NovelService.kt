package com.teamwss.websoso.data.remote.service

import com.teamwss.websoso.data.remote.request.NovelPostRequest
import com.teamwss.websoso.data.remote.response.NovelPostResponse
import com.teamwss.websoso.data.remote.response.SearchNovelsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NovelService {
    @GET("novels/{novelId}")
    suspend fun getPostNovelInfo(
        @Path("novelId") novelId: Long
    ): NovelPostResponse

    @POST("user-novels/{novelId}")
    suspend fun postPostNovelInfo(
        @Path("novelId") novelId: Long,
        @Body request: NovelPostRequest,
    ): Response<Unit>

    @GET("novels")
    suspend fun getNovels(
        @Query("lastNovelId") lastNovelId: Long,
        @Query("size") size: Int,
        @Query("word") word: String,
    ): SearchNovelsResponse
}