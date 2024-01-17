package com.teamwss.websoso.data.remote.response


import com.teamwss.websoso.ui.postNovel.model.PostNovelInfoModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserNovelPostResponse(
    @SerialName("userNovelId")
    val userNovelId: Long,
    @SerialName("userNovelTitle")
    val userNovelTitle: String,
    @SerialName("userNovelAuthor")
    val userNovelAuthor: String,
    @SerialName("userNovelGenre")
    val userNovelGenre: String,
    @SerialName("userNovelImg")
    val userNovelImg: String,
    @SerialName("userNovelDescription")
    val userNovelDescription: String,
    @SerialName("userNovelRating")
    val userNovelRating: Float?,
    @SerialName("userNovelReadStatus")
    val userNovelReadStatus: String,
    @SerialName("platforms")
    val platforms: List<NovelPlatformPostResponse>,
    @SerialName("userNovelReadDate")
    val userNovelReadDate: UserNovelReadDate,
) {
    @Serializable
    data class UserNovelReadDate(
        @SerialName("userNovelReadEndDate")
        val userNovelReadEndDate: String?,
        @SerialName("userNovelReadStartDate")
        val userNovelReadStartDate: String?,
    )

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
            readStartDate = userNovelReadDate.userNovelReadStartDate,
            readEndDate = userNovelReadDate.userNovelReadEndDate
        )
    }
}