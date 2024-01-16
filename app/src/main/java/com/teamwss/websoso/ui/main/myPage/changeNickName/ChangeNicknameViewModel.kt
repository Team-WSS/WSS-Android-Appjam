package com.teamwss.websoso.ui.main.myPage.changeNickName

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwss.websoso.data.ServicePool
import com.teamwss.websoso.data.remote.request.NicknamePatchRequest
import kotlinx.coroutines.launch

class ChangeNicknameViewModel : ViewModel() {
    private val _userNickname = MutableLiveData<String>()
    val userNickname: LiveData<String> get() = _userNickname

    private val _patchSuccess = MutableLiveData<Boolean>()
    val patchSuccess: LiveData<Boolean> get() = _patchSuccess

    fun patchUserNickName(userNickname: String) {
        viewModelScope.launch {
            runCatching {
                ServicePool.authService.patchNickname(
                    NicknamePatchRequest(
                        userNickname = userNickname
                    )
                )
            }.onSuccess {
                _patchSuccess.value = true
            }.onFailure {
                _patchSuccess.value = false
            }
        }
    }
}