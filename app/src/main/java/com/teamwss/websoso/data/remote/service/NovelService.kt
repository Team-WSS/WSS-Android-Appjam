package com.teamwss.websoso.data.remote.service

import com.teamwss.websoso.data.remote.request.PostNovelRequest
import com.teamwss.websoso.data.remote.response.EditNovelResponse
import com.teamwss.websoso.data.remote.response.PostNovelResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface NovelService {
    @GET("/novels/{novelId}")
    suspend fun fetchPostNovelInfo(
        @Path("novelId") novelId: Long
    ): PostNovelResponse

    @PATCH("/user-novels/{userNovelId}")
    suspend fun editPostNovelInfo(
        @Path("userNovelId") userNovelId: Long,
        @Body request: PostNovelRequest,
    ): PostNovelRequest
}