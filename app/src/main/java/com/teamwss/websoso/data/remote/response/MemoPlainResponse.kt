package com.teamwss.websoso.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemoPlainResponse(
    @SerialName("userNovelTitle")
    val userNovelTitle: String,
    @SerialName("userNovelImg")
    val userNovelImg: String,
    @SerialName("userNovelAuthor")
    val userNovelAuthor: String,
    @SerialName("memoDate")
    val memoDate: String,
    @SerialName("memoContent")
    val memoContent: String,
)