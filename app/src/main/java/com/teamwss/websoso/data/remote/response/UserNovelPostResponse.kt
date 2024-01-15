package com.teamwss.websoso.data.remote.response

import com.teamwss.websoso.ui.postNovel.postNovelModel.PostNovelInfoModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserNovelPostResponse(
    @SerialName("platforms")
    val platforms: List<NovelPlatformPostResponse>,
    @SerialName("userNovelReadEndDate")
    val userNovelReadEndDate: String? = null,
    @SerialName("userNovelReadStartDate")
    val userNovelReadStartDate: String? = null,
    @SerialName("userNovelAuthor")
    val userNovelAuthor: String,
    @SerialName("userNovelDescription")
    val userNovelDescription: String,
    @SerialName("userNovelGenre")
    val userNovelGenre: String,
    @SerialName("userNovelId")
    val userNovelId: Long,
    @SerialName("userNovelImg")
    val userNovelImg: String,
    @SerialName("userNovelRating")
    val userNovelRating: Float?,
    @SerialName("userNovelReadStatus")
    val userNovelReadStatus: String,
    @SerialName("userNovelTitle")
    val userNovelTitle: String,
) {
    fun toUI(): PostNovelInfoModel {
        return PostNovelInfoModel(
            author = userNovelAuthor,
            description = userNovelDescription,
            genre = userNovelGenre,
            id = userNovelId,
            image = userNovelImg,
            title = userNovelTitle,
            platforms = platforms,
            rating = userNovelRating,
            readStatus = userNovelReadStatus,
            readStartDate = userNovelReadStartDate,
            readEndDate = userNovelReadEndDate
        )
    }
}