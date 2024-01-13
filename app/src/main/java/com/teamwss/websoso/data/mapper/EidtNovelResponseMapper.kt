package com.teamwss.websoso.data.mapper

import com.teamwss.websoso.data.remote.response.EditNovelResponse
import com.teamwss.websoso.ui.postNovel.postNovelModel.PostNovelInfoModel

fun EditNovelResponse.toUI(): PostNovelInfoModel {
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