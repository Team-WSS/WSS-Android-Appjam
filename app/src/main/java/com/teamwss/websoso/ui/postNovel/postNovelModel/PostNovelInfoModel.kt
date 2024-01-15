package com.teamwss.websoso.ui.postNovel.postNovelModel

import com.teamwss.websoso.data.remote.response.NovelPlatformPostResponse
import com.teamwss.websoso.ui.common.model.ReadStatus
import java.time.LocalDate

data class PostNovelInfoModel(
    val author: String,
    val description: String,
    val genre: String,
    val id: Long,
    val image: String,
    val title: String,
    val platforms: List<NovelPlatformPostResponse>,
    val rating: Float? = 0F,
    val readStatus: String = ReadStatus.FINISH.toString(),
    val readStartDate: String? = LocalDate.now().toString(),
    val readEndDate: String? = LocalDate.now().toString(),
)