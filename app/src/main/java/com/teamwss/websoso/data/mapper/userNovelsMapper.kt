package com.teamwss.websoso.data.mapper

import com.teamwss.websoso.data.model.LibraryUserNovelEntity
import com.teamwss.websoso.data.remote.response.UserNovel


fun List<UserNovel>.toData(): List<LibraryUserNovelEntity> {
    return this.map {
        LibraryUserNovelEntity(
            userNovelId = it.userNovelId,
            userNovelCover = it.userNovelImg,
            userNovelAuthor = it.userNovelAuthor,
            userNovelRating = it.userNovelRating,
            userNovelTitle = it.userNovelTitle,
        )
    }
}
