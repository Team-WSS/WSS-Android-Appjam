package com.teamwss.websoso.data.repository

import com.teamwss.websoso.data.remote.request.UserNovelsLibraryRequest
import com.teamwss.websoso.data.remote.service.UserNovelService

class UserNovelsRepository(
    private val userNovelService: UserNovelService
) {

    suspend fun getNovels(request: UserNovelsLibraryRequest) = userNovelService.getNovels(
        readStatus = request.readStatus,
        lastUserNovelId = request.lastUserNovelId,
        size = request.size,
        sortType = request.sortType.toString()
    )

}