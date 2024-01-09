package com.teamwss.websoso.data.remote.response

data class NovelMemoInfoDTO(
    val memos: List<NovelMemo>,
    val platforms: List<Platform>,
    val userNovelGenre: String,
    val userNovelRating: Double,
    val userNovelReadEndDate: String,
    val userNovelReadStartDate: String,
    val userNovelReadStatus: String,
    val userNovelDescription: String,
)