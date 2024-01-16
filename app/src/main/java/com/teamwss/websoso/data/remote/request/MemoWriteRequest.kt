package com.teamwss.websoso.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MemoWriteRequest(
    @SerialName("memoContent")
    val memoContent: String,
)