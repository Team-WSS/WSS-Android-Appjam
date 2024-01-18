package com.teamwss.websoso.ui.main.myPage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.teamwss.websoso.App
import com.teamwss.websoso.data.model.AvatarEntity
import com.teamwss.websoso.data.model.UserInfoMyPageEntity
import com.teamwss.websoso.data.repository.AuthRepository
import com.teamwss.websoso.data.repository.AvatarRepository
import com.teamwss.websoso.ui.main.myPage.model.Avatar
import kotlinx.coroutines.launch

class MyPageViewModel(
    private val authRepository: AuthRepository,
    private val avatarRepository: AvatarRepository
) : ViewModel() {
    private var _avatars = MutableLiveData<List<Avatar>>()
    val avatars: MutableLiveData<List<Avatar>> get() = _avatars

    private var _userInfo = MutableLiveData<UserInfoMyPageEntity>()
    val userInfo: LiveData<UserInfoMyPageEntity> get() = _userInfo

    private var _selectedAvatar = MutableLiveData<AvatarEntity>()
    val selectedAvatar: LiveData<AvatarEntity> get() = _selectedAvatar

    private var _selectedAvatarId = MutableLiveData<Long>()
    val selectedAvatarId: LiveData<Long> get() = _selectedAvatarId

    private var _patchSuccess = MutableLiveData<Boolean>()
    val patchSuccess: LiveData<Boolean> get() = _patchSuccess

    init {
        getMyPageUserInfo()
    }

    fun getMyPageUserInfo() {
        viewModelScope.launch {
            runCatching {
                authRepository.getMyPageUserInfo()
            }.onSuccess { result ->
                val newAvatars = result.userAvatars.map {
                    Avatar(
                        avatarId = it.avatarId,
                        avatarImg = it.avatarImg,
                        hasAvatar = it.hasAvatar,
                        isRepresentativeAvatar = result.representativeAvatarId == it.avatarId
                    )
                }
                _userInfo.value = result
                _avatars.value = newAvatars
            }.onFailure {
                Log.d("error", it.toString())
            }
        }
    }

    fun getAvatar(id: Long) {
        viewModelScope.launch {
            runCatching {
                avatarRepository.getAvatarInfo(id)
            }.onSuccess {
                _selectedAvatar.value = it
                _selectedAvatarId.value = id
            }.onFailure {
                Log.e("getAvatar error", it.toString())
            }
        }
    }

    fun patchRepresentativeAvatar() {
        viewModelScope.launch {
            runCatching {
                selectedAvatarId.value?.let { avatarRepository.patchRepresentativeAvatar(it) }
            }.onSuccess {
                Log.e("patchAvatar", "patchAvatar: Success $it")
                _patchSuccess.value = true
            }.onFailure {
                Log.e("patchAvatar error", it.toString())
            }
        }
    }

    fun setPatchSuccess(success: Boolean) {
        _patchSuccess.value = success
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val authRepository: AuthRepository = App.getAuthRepository()
                val avatarRepository: AvatarRepository = App.getAvatarRepository()
                MyPageViewModel(
                    authRepository = authRepository,
                    avatarRepository = avatarRepository
                )
            }
        }
    }
}


