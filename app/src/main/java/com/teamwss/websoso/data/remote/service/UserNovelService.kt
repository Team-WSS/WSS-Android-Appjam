package com.teamwss.websoso.data.remote.service

import com.teamwss.websoso.data.remote.request.PostNovelRequest
import com.teamwss.websoso.data.remote.response.EditNovelResponse
import com.teamwss.websoso.data.remote.response.PostNovelResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
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
    ): EditNovelResponse

    @POST("/user-novels/{novelId}")
    suspend fun postPostNovelInfo(
        @Path("novelId") novelId: Long,
        @Body request: PostNovelRequest,
    ): PostNovelRequest
}