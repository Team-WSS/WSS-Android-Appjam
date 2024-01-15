package com.teamwss.websoso.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemoWriteResponse (
    @SerialName("isAvatarUnlocked")
    val isAvatarUnlocked: Boolean,
)