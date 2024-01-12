package com.teamwss.websoso.data.remote.api

import com.teamwss.websoso.data.remote.request.PostNovelRequest
import com.teamwss.websoso.data.remote.response.EditNovelResponse
import com.teamwss.websoso.data.remote.response.PostNovelResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface WssService {

    @Headers("Authorization")
    @GET("/novels/{novelId}")
    suspend fun getPostNovelInfo(
        @Path("novelId") novelId: Long
    ): PostNovelResponse

    @Headers("Authorization")
    @GET("/novels/{novelId}")
    suspend fun getEditNovelInfo(
        @Path("novelId") novelId: Long
    ): EditNovelResponse

    @Headers("Authorization")
    @POST("/user-novels/{novelId}")
    suspend fun postPostNovelInfo(
        @Path("novelId") novelId: Long
    ): PostNovelRequest

    @Headers("Authorization")
    @PATCH("/user-novels/{userNovelId}")
    suspend fun editPostNovelInfo(
        @Path("userNovelId") userNovelId: Long
    ): PostNovelRequest

}