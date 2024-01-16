package com.teamwss.websoso.data.repository

import com.teamwss.websoso.data.mapper.SosoPickNovelResponseMapper.toData
import com.teamwss.websoso.data.mapper.UserNovelMapper.toData
import com.teamwss.websoso.data.model.LibraryUserNovelEntity
import com.teamwss.websoso.data.model.SosoPickNovelEntity
import com.teamwss.websoso.data.remote.service.UserNovelService

class UserNovelsRepository(
    private val userNovelService: UserNovelService
) {

    suspend fun getUserNovels(
        readState: String,
        lastUserNovelId: Long,
        size: Int,
        sortType: String
    ): Pair<List<LibraryUserNovelEntity>, Long> {
        val response = userNovelService.getUserNovels(
            readStatus = readState,
            lastUserNovelId = lastUserNovelId,
            size = size,
            sortType = sortType
        )
        val novels = response.userNovelResponses.toData()
        return Pair(novels, response.userNovelCount)
    }

    suspend fun getSosoPickNovels(): List<SosoPickNovelEntity> =
        userNovelService.getSosoPickNovels().sosoPickNovelResponses.toData()

}