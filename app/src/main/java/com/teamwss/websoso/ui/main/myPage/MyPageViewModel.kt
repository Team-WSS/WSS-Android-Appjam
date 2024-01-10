package com.teamwss.websoso.ui.main.myPage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamwss.websoso.ui.main.myPage.model.Avatar

class MyPageViewModel : ViewModel() {
    private val _avatarData = MutableLiveData<List<Avatar>>()
    val avatarData: MutableLiveData<List<Avatar>> get() = _avatarData

    init {
        _avatarData.value = getMockAvatarList()
    }

    private fun getMockAvatarList(): List<Avatar> {
        return listOf(
            Avatar(
                avatarImg = "https://github.com/Team-WSS/WSS-Android/assets/144861180/4f4e5c2d-0fb8-4425-a5c0-f42d45010326",
            ),
            Avatar(
                avatarImg = "https://github.com/Team-WSS/WSS-Android/assets/144861180/4f4e5c2d-0fb8-4425-a5c0-f42d45010326",
            ),
            Avatar(
                avatarImg = "https://github.com/Team-WSS/WSS-Android/assets/144861180/4f4e5c2d-0fb8-4425-a5c0-f42d45010326",
            ),
            Avatar(
                avatarImg = "https://github.com/Team-WSS/WSS-Android/assets/144861180/4f4e5c2d-0fb8-4425-a5c0-f42d45010326",
            ),
            Avatar(
                avatarImg = "https://github.com/Team-WSS/WSS-Android/assets/144861180/4f4e5c2d-0fb8-4425-a5c0-f42d45010326",
            ),
        )
    }
}