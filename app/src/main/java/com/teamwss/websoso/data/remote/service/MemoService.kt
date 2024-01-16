package com.teamwss.websoso.data.remote.service

import com.teamwss.websoso.data.remote.request.MemoWriteRequest
import com.teamwss.websoso.data.remote.response.RecordResponse
import com.teamwss.websoso.data.remote.response.MemoPlainResponse
import com.teamwss.websoso.data.remote.response.MemoWriteResponse
import com.teamwss.websoso.ui.memoWrite.MemoWriteActivity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
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

    @POST("/user-novels/{userNovelId}/memo")
    suspend fun postMemo(
        @Path("userNovelId") userNovelId: Long,
        @Body request: MemoWriteRequest
    ): MemoWriteResponse

    @PATCH("/memos/{memoId}")
    suspend fun patchNovel(
        @Path("memoId") memoId: Long,
        @Body request: MemoWriteRequest
    ): Response<Unit>
}