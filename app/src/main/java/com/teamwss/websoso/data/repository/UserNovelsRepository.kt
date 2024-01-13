package com.teamwss.websoso.data.repository

import UserNovelsApi
import com.teamwss.websoso.data.remote.request.UserNovelsRequest

class UserNovelsRepository (
    private val userNovelsApi: UserNovelsApi
) {

    suspend fun getNovels(request: UserNovelsRequest) = userNovelsApi.getNovels(
        readStatus = request.readStatus,
        lastUserNovelId = request.lastUserNovelId,
        size = request.size,
        sortType = request.sortType.toString()
    )
}