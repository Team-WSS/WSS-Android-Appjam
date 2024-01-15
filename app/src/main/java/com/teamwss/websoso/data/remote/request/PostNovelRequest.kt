package com.teamwss.websoso.data.remote.request

import com.teamwss.websoso.ui.postNovel.postNovelModel.ReadStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostNovelRequest(
    @SerialName("userNovelRating")
    val userNovelRating: Float?,
    @SerialName("userNovelReadStatus")
    val userNovelReadStatus: String,
    @SerialName("userNovelReadStartDate")
    val userNovelReadStartDate: String?,
    @SerialName("userNovelReadEndDate")
    val userNovelReadEndDate: String?,
)