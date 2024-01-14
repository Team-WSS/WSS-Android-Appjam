package com.teamwss.websoso.data.remote.request

import com.teamwss.websoso.ui.postNovel.postNovelModel.ReadStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostNovelRequest(
    @SerialName("userNovelRating")
    val userNovelRating: Float = 0f,
    @SerialName("userNovelReadStatus")
    val userNovelReadStatus: String = ReadStatus.READING.toString(),
    @SerialName("userNovelReadStartDate")
    val userNovelReadStartDate: String? = null,
    @SerialName("userNovelReadEndDate")
    val userNovelReadEndDate: String? = null,
)