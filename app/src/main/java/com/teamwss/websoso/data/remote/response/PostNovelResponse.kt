package com.teamwss.websoso.data.remote.response

import com.teamwss.websoso.ui.postNovel.postNovelModel.PostNovelInfoModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostNovelResponse(
    @SerialName("novelAuthor")
    val novelAuthor: String,
    @SerialName("novelDescription")
    val novelDescription: String,
    @SerialName("novelGenre")
    val novelGenre: String,
    @SerialName("novelId")
    val novelId: Long,
    @SerialName("novelImg")
    val novelImg: String,
    @SerialName("novelTitle")
    val novelTitle: String,
    @SerialName("platforms")
    val platforms: List<GetPlatformResponse>,
) {
    fun toUI(): PostNovelInfoModel {
        return PostNovelInfoModel(
            author = novelAuthor,
            description = novelDescription,
            genre = novelGenre,
            id = novelId,
            image = novelImg,
            title = novelTitle,
            platforms = platforms
        )
    }
}