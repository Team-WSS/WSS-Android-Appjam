package com.teamwss.websoso.data.mapper

import com.teamwss.websoso.data.remote.response.PostNovelResponse
import com.teamwss.websoso.ui.postNovel.postNovelModel.PostNovelInfoModel

fun PostNovelResponse.toUI(): PostNovelInfoModel {
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
