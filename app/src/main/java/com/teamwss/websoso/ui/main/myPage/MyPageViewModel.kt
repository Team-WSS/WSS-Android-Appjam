package com.teamwss.websoso.ui.main.myPage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwss.websoso.data.ServicePool
import com.teamwss.websoso.data.remote.response.AvatarResponse
import com.teamwss.websoso.data.remote.response.UserAvatarResponse
import com.teamwss.websoso.ui.main.myPage.model.Avatar
import kotlinx.coroutines.launch

class MyPageViewModel : ViewModel() {
    private val _avatars = MutableLiveData<List<Avatar>>()
    val avatars: MutableLiveData<List<Avatar>> get() = _avatars

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName
    private val _userNovelCount = MutableLiveData<Long>()
    val userNovelCount: LiveData<Long> get() = _userNovelCount
    private val _memoCount = MutableLiveData<Long>()
    val memoCount: LiveData<Long> get() = _memoCount
    private val _representativeAvatarTag = MutableLiveData<String>()
    val representativeAvatarTag: LiveData<String> get() = _representativeAvatarTag
    private val _representativeAvatarLineContent = MutableLiveData<String>()
    val representativeAvatarLineContent: LiveData<String> get() = _representativeAvatarLineContent
    private val _representativeAvatarImg = MutableLiveData<String>()
    val representativeAvatarImg: LiveData<String> get() = _representativeAvatarImg
    private val _representativeAvatarGenreBadge = MutableLiveData<String>()
    val representativeAvatarGenreBadge: LiveData<String> get() = _representativeAvatarGenreBadge

    private val _newAvatar = MutableLiveData<AvatarResponse>()
    val newAvatar: LiveData<AvatarResponse> get() = _newAvatar

    private val _representativeAvatarId = MutableLiveData<Long>()
    val representativeAvatarId: LiveData<Long> get() = _representativeAvatarId


    init {
        updateUserInfo()
    }

    private fun updateUserInfo() {
        viewModelScope.launch {
            runCatching {
                ServicePool.authService.getMyPageUserInfo()
            }.onSuccess { result ->
                _avatars.value = result.userAvatars.map {
                    Avatar(
                        avatarId = it.avatarId,
                        avatarImg = it.avatarImg,
                        hasAvatar = it.hasAvatar,
                    )
                }
                _userName.value = result.userNickName
                _userNovelCount.value = result.userNovelCount
                _memoCount.value = result.memoCount
                _representativeAvatarLineContent.value = result.representativeAvatarLineContent
                _representativeAvatarTag.value = result.representativeAvatarTag
                _representativeAvatarGenreBadge.value = result.representativeAvatarGenreBadge
                _representativeAvatarImg.value = result.representativeAvatarImg
                _representativeAvatarId.value = result.representativeAvatarId
            }.onFailure {
                Log.d("error", it.toString())
            }
        }
    }

    fun getAvatar(id: Long) {
        viewModelScope.launch {
            runCatching {
                ServicePool.avatarService.getAvatarInfo(id)
            }.onSuccess { result ->
                _newAvatar.value = result
            }.onFailure {
                Log.d("error", it.toString())
            }
        }
    }
}


