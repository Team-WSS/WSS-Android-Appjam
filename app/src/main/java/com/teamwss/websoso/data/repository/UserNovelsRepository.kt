package com.teamwss.websoso.data.repository

import com.teamwss.websoso.data.remote.request.LoginRequest
import com.teamwss.websoso.data.remote.request.UserNovelsRequest
import com.teamwss.websoso.data.remote.service.LibraryService

class UserNovelsRepository(
    private val libraryService: LibraryService
) {

    suspend fun getNovels(request: UserNovelsRequest) = libraryService.getNovels(
        readStatus = request.readStatus,
        lastUserNovelId = request.lastUserNovelId,
        size = request.size,
        sortType = request.sortType.toString()
    )

    suspend fun login(request: LoginRequest) = libraryService.login(
        loginRequest = request
    )
}