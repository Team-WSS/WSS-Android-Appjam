package com.teamwss.websoso.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecordResponse(
    @SerialName("memoCount")
    val memoCount: Long,
    @SerialName("memos")
    val memos: List<RecordMemoResponse>,
)