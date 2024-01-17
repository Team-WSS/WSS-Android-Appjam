package com.teamwss.websoso.ui.postNovel.model

import com.teamwss.websoso.data.remote.response.NovelPlatformPostResponse
import com.teamwss.websoso.ui.common.model.ReadStatus
import java.time.LocalDate

data class PostNovelInfoModel(
    var id: Long,
    val title: String,
    val author: String,
    val genre: String,
    val image: String,
    val description: String,
    val rating: Float? = 0F,
    val readStatus: String = ReadStatus.FINISH.toString(),
    val readStartDate: String? = LocalDate.now().toString(),
    val readEndDate: String? = LocalDate.now().toString(),
    val platforms: List<NovelPlatformPostResponse>
)