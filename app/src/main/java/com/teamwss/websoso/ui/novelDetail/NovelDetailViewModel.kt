package com.teamwss.websoso.ui.novelDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwss.websoso.data.ServicePool
import com.teamwss.websoso.data.remote.response.NovelMemoInfoResponse
import kotlinx.coroutines.launch

class NovelDetailViewModel : ViewModel() {
    private val _userNovelMemoInfoResponse: MutableLiveData<NovelMemoInfoResponse> =
        MutableLiveData()
    val userNovelMemoInfoResponse: LiveData<NovelMemoInfoResponse> = _userNovelMemoInfoResponse

    private val _userNovelId: MutableLiveData<Long> = MutableLiveData()
    val userNovelId: LiveData<Long> = _userNovelId

    fun getUserNovelMemoInfo(userNovelId: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                ServicePool.userNovelService.getUserNovelMemoInfo(userNovelId)
            }.onSuccess { response ->
                _userNovelMemoInfoResponse.value = response
                Log.d("tongsin", response.toString())
                Log.d("tongsin", "success")
            }.onFailure { throwable ->
                Log.e("tongsin", throwable.toString())
                Log.d("tongsin",userNovelId.toString())
            }
        }
    }

    fun getUserNovelId(userNovelId: Long) {
        _userNovelId.value = userNovelId
    }
}