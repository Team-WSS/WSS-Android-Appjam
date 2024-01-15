package com.teamwss.websoso.ui.main.myPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamwss.websoso.ui.main.myPage.model.Avatar

class MyPageViewModel : ViewModel() {
    private val _avatars = MutableLiveData<List<Avatar>>()
    val avatars: MutableLiveData<List<Avatar>> get() = _avatars

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    init {
        _avatars.value = getMockAvatarList()
        _userName.value = "김명진"
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