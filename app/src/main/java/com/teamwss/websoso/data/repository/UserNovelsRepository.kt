package com.teamwss.websoso.data.repository

import com.teamwss.websoso.data.remote.service.UserNovelService

class UserNovelsRepository(
    private val userNovelService: UserNovelService
) {

    suspend fun getNovels(readState: String, lastUserNovelId: Long, size: Int, sortType: String) =
        userNovelService.getNovels(
            readStatus = readState,
            lastUserNovelId = lastUserNovelId,
            size = size,
            sortType = sortType
        )

}