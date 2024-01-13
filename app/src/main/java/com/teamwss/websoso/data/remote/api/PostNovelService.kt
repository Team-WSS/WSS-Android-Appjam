package com.teamwss.websoso.data.remote.api

import com.teamwss.websoso.data.remote.request.PostNovelRequest
import com.teamwss.websoso.data.remote.response.EditNovelResponse
import com.teamwss.websoso.data.remote.response.PostNovelResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface PostNovelService {

    @GET("/novels/{novelId}")
    suspend fun getPostNovelInfo(
        @Path("novelId") novelId: Long
    ): PostNovelResponse

    @GET("/novels/{novelId}")
    suspend fun getEditNovelInfo(
        @Path("novelId") novelId: Long
    ): EditNovelResponse

    @POST("/user-novels/{novelId}")
    suspend fun postPostNovelInfo(
        @Path("novelId") novelId: Long
    ): PostNovelRequest

    @PATCH("/user-novels/{userNovelId}")
    suspend fun editPostNovelInfo(
        @Path("userNovelId") userNovelId: Long
    ): PostNovelRequest

}