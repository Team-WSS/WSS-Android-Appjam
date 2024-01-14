package com.teamwss.websoso.data.remote.response

data class NovelMemoInfoResponse(
    val memos: List<NovelMemoResponse>,
    val platforms: List<PlatformGetResponse>,
    val userNovelGenre: String,
    val userNovelRating: Float,
    val userNovelReadEndDate: String,
    val userNovelReadStartDate: String,
    val userNovelReadStatus: String,
    val userNovelDescription: String,
)