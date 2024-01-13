package com.teamwss.websoso.data.remote.request

import com.teamwss.websoso.ui.main.library.model.SortType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserNovelsRequest (
    @SerialName("readStatus")
    val readStatus : String,
    @SerialName("lastUserNovelId")
    val lastUserNovelId : Long,
    @SerialName("size")
    val size : Int,
    @SerialName("sortType")
    val sortType: String
)