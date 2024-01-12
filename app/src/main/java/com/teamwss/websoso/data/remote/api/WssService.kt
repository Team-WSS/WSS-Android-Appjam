package com.teamwss.websoso.data.remote.api

import com.teamwss.websoso.data.remote.response.PostNovelResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface WssService {

    @Headers("Authorization")
    @GET("/novels/{novelId}")
    suspend fun getPostNovelInfo(
        @Path("novelId") novelId: Long
    ): PostNovelResponse

}