package com.teamwss.websoso.ui.main.myPage.model

data class Avatar(
    val avatarId:Long,
    val avatarImg: String,
    val hasAvatar:Boolean,
    val isRepresentativeAvatar : Boolean = false,
)
