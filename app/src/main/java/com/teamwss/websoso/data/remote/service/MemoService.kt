package com.teamwss.websoso.data.remote.service

import com.teamwss.websoso.data.remote.response.RecordResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MemoService {
    @GET("/memos")
    suspend fun getRecord(
        @Query("lastMemoId") lastUserNovelId: Long,
        @Query("size") size: Int,
        @Query("sortType") sortType: String
    ): RecordResponse
}