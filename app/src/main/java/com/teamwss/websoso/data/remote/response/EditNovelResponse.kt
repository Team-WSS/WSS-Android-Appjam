package com.teamwss.websoso.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class EditNovelResponse(
    @SerialName("platformList")
    val platformList: List<PlatformGetResponse> = listOf(),
    @SerialName("readEndDate")
    val readEndDate: String? = LocalDate.now().toString(),
    @SerialName("readStartDate")
    val readStartDate: String? = LocalDate.now().toString(),
    @SerialName("userNovelAuthor")
    val userNovelAuthor: String = "",
    @SerialName("userNovelDescription")
    val userNovelDescription: String = "",
    @SerialName("userNovelGenre")
    val userNovelGenre: String = "",
    @SerialName("userNovelId")
    val userNovelId: Long = 0,
    @SerialName("userNovelImg")
    val userNovelImg: String = "",
    @SerialName("userNovelRating")
    val userNovelRating: Float = 0f,
    @SerialName("userNovelReadStatus")
    val userNovelReadStatus: String = "",
    @SerialName("userNovelTitle")
    val userNovelTitle: String = "",
)