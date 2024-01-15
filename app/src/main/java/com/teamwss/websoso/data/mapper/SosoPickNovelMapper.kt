package com.teamwss.websoso.data.mapper

import com.teamwss.websoso.data.model.SosoPickNovelEntity
import com.teamwss.websoso.data.remote.response.SosoPickNovel

object SosoPickNovelMapper {

    fun List<SosoPickNovel>.toData(): List<SosoPickNovelEntity> {
        return this.map {
            SosoPickNovelEntity(
                novelAuthor = it.novelAuthor,
                novelImg = it.novelImg,
                novelRegisteredCount = it.novelRegisteredCount,
                novelTitle = it.novelTitle
            )
        }
    }
}