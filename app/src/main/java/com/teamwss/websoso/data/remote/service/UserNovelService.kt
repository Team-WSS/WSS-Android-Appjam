package com.teamwss.websoso.data.remote.service

import com.teamwss.websoso.data.remote.response.UserNovelsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserNovelService {

    @GET("user-novels")
    suspend fun getNovels(
        @Query("readStatus") readStatus: String,
        @Query("lastUserNovelId") lastUserNovelId: Long,
        @Query("size") size: Int,
        @Query("sortType") sortType: String
    ): UserNovelsResponse

}