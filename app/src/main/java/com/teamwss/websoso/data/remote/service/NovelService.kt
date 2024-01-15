package com.teamwss.websoso.data.remote.service

import com.teamwss.websoso.data.remote.request.PostNovelRequest
import com.teamwss.websoso.data.remote.response.EditNovelResponse
import com.teamwss.websoso.data.remote.response.PostNovelResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface NovelService {
    @GET("/novels/{novelId}")
    suspend fun fetchPostNovelInfo(
        @Path("novelId") novelId: Long
    ): PostNovelResponse

    @POST("/user-novels/{novelId}")
    suspend fun postPostNovelInfo(
        @Path("novelId") novelId: Long,
        @Body request: PostNovelRequest,
    ): Response<Unit>
}