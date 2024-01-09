package com.teamwss.websoso.ui.search.model

import androidx.annotation.DrawableRes

data class SearchResult(
    @DrawableRes val resultNovelImage: Int,
    val resultNovelTitle: String,
    val resultNovelAuthor: String,
    val resultNovelGenre: String,
)