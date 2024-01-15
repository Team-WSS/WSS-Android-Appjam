package com.teamwss.websoso.data.remote.service

import com.teamwss.websoso.data.remote.response.RecordResponse
import com.teamwss.websoso.data.remote.response.MemoPlainResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface MemoService {

    @GET("/memos")
    suspend fun getRecord(
        @Query("lastMemoId") lastUserNovelId: Long,
        @Query("size") size: Int,
        @Query("sortType") sortType: String
    ): RecordResponse

    @GET("/memos/{memoId}")
    suspend fun getMemo(
        @Path("memoId") memoId: Long
    ): MemoPlainResponse

    @DELETE("/memos/{memoId}")
    suspend fun deleteMemo(
        @Path("memoId") memoId: Long
    ): Response<Unit>
}