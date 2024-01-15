package com.teamwss.websoso.ui.novelDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwss.websoso.data.ServicePool
import com.teamwss.websoso.data.remote.response.NovelMemoInfoResponse
import com.teamwss.websoso.data.remote.response.NovelMemoResponse
import kotlinx.coroutines.launch

class NovelDetailViewModel : ViewModel() {
    private val _userNovelMemoInfoResponse: MutableLiveData<NovelMemoInfoResponse> =
        MutableLiveData()
    val userNovelMemoInfoResponse: LiveData<NovelMemoInfoResponse> = _userNovelMemoInfoResponse

    private val _userNovelId: MutableLiveData<Long> = MutableLiveData()
    val userNovelId: LiveData<Long> = _userNovelId

    private val _memos: MutableLiveData<List<NovelMemoResponse>> = MutableLiveData()
    val memos : LiveData<List<NovelMemoResponse>> = _memos

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
                Log.d("tongsin", userNovelId.toString())
            }
        }
    }

    fun deleteNovel(userNovelId: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                ServicePool.userNovelService.deleteUserNovel(userNovelId)
            }.onSuccess {
                Log.d("tongsin", "success")
            }.onFailure { throwable ->
                Log.e("tongsin", throwable.toString())
            }
        }
    }

    fun getUserNovelId(userNovelId: Long) {
        _userNovelId.value = userNovelId
    }
}