package com.teamwss.websoso.data.mapper

import com.teamwss.websoso.data.model.LibraryUserNovel
import com.teamwss.websoso.data.remote.response.UserNovel


fun List<UserNovel>.toData(): List<LibraryUserNovel> {
    return this.map {
        LibraryUserNovel(
            userNovelId = it.userNovelId,
            userNovelImg = it.userNovelImg,
            userNovelAuthor = it.userNovelAuthor,
            userNovelRating = it.userNovelRating,
            userNovelTitle = it.userNovelTitle,
        )
    }
}
