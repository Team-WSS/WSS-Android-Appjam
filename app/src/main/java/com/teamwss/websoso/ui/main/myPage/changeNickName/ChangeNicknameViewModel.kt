package com.teamwss.websoso.ui.main.myPage.changeNickName

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwss.websoso.data.ServicePool
import com.teamwss.websoso.data.remote.request.NicknamePatchRequest
import kotlinx.coroutines.launch

class ChangeNicknameViewModel : ViewModel() {
    var userNickname = MutableLiveData<String>()

    val userNameLength = MediatorLiveData<Int>().apply {
        addSource(userNickname) { name ->
            value = name?.length ?: 0
        }
    }

    private val _patchSuccess = MutableLiveData<Boolean>()
    val patchSuccess: LiveData<Boolean> get() = _patchSuccess

    fun setUserNickname(userNickname: String) {
        this.userNickname.value = userNickname
    }

    fun checkNicknameEmpty(): Boolean {
        return userNickname.value.isNullOrEmpty()
    }

    fun patchUserNickName() {
        viewModelScope.launch {
            runCatching {
                ServicePool.authService.patchNickname(
                    NicknamePatchRequest(
                        userNickname = userNickname.value.orEmpty()
                    )
                )
            }.onSuccess {
                _patchSuccess.value = true
            }.onFailure {
                _patchSuccess.value = false
            }
        }
    }

    companion object {
        const val MAX_LENGTH = 10
    }
}