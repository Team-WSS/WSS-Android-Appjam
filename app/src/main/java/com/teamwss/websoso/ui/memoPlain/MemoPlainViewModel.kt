package com.teamwss.websoso.ui.memoPlain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwss.websoso.data.ServicePool
import com.teamwss.websoso.data.remote.response.MemoPlainResponse
import kotlinx.coroutines.launch

class MemoPlainViewModel : ViewModel() {
    private var _memo: MutableLiveData<MemoPlainResponse> = MutableLiveData()
    val memo: LiveData<MemoPlainResponse> = _memo

    private var _memoId: MutableLiveData<Long> = MutableLiveData()
    val memoId: LiveData<Long> = _memoId

    fun getMemo(memoId: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                ServicePool.memoService.getMemo(memoId)
            }.onSuccess { response ->
                _memo.value = response
            }.onFailure { throwable ->
                Log.e("tongsinMemoWrite", throwable.toString())
            }
        }
    }

    fun deleteMemo(memoId: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                ServicePool.memoService.deleteMemo(memoId)
            }.onSuccess {
                Log.d("tongsinMemoPlain", "success")
            }.onFailure { throwable ->
                Log.e("tongsinMemoPlain", throwable.toString())
            }
        }
    }

    fun updateMemoId(memoId: Long) {
        _memoId.value = memoId
    }
}