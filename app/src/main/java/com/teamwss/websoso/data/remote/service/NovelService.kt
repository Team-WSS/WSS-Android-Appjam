package com.teamwss.websoso.data.remote.service

import com.teamwss.websoso.data.remote.request.NovelPostRequest
import com.teamwss.websoso.data.remote.response.NovelPostResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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
}