package com.teamwss.websoso.data.repository

import com.teamwss.websoso.data.remote.response.SosopickNovelHomeResponse
import com.teamwss.websoso.data.remote.response.UserNovelsLibraryResponse
import com.teamwss.websoso.data.remote.service.UserNovelService

class UserNovelsRepository(
    private val userNovelService: UserNovelService
) {

    suspend fun getUserNovels(
        readState: String,
        lastUserNovelId: Long,
        size: Int,
        sortType: String
    ): UserNovelsLibraryResponse =
        userNovelService.getUserNovels(
            readStatus = readState,
            lastUserNovelId = lastUserNovelId,
            size = size,
            sortType = sortType
        )

    suspend fun getSosoPickNovels(): SosopickNovelHomeResponse =
        userNovelService.getSosoPickNovels()

}