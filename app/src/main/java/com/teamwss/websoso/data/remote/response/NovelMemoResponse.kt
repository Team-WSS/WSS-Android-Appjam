package com.teamwss.websoso.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NovelMemoResponse(
    @SerialName("memoId")
    val memoId: Long,
    @SerialName("memoContent")
    val memoContent: String,
    @SerialName("memoDate")
    val memoDate: String,
)