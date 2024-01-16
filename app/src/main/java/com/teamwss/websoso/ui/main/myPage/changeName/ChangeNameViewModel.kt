package com.teamwss.websoso.ui.main.myPage.changeName

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwss.websoso.data.ServicePool
import com.teamwss.websoso.data.remote.request.NicknamePatchRequest
import kotlinx.coroutines.launch

class ChangeNameViewModel:ViewModel() {
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _patchResult = MutableLiveData<Result<Unit>>()
    val patchResult: LiveData<Result<Unit>> get() = _patchResult

    init {
        patchUserNickName(_userName.value.orEmpty())
    }

    fun patchUserNickName(userName: String) {
        viewModelScope.launch {
            _patchResult.value = runCatching {
                ServicePool.authService.patchNickname(
                    NicknamePatchRequest(
                        userNickname = userName
                    )
                )
            }
        }
    }
}