package com.teamwss.websoso.ui.memoWrite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwss.websoso.data.ServicePool
import com.teamwss.websoso.data.remote.request.MemoWriteRequest
import kotlinx.coroutines.launch

class MemoWriteViewModel : ViewModel() {
    private var _memoContent: MutableLiveData<String> = MutableLiveData()
    val memoContent: MutableLiveData<String> = _memoContent

    private var _isAvatarUnlocked: MutableLiveData<Boolean> = MutableLiveData()
    val isAvatarUnlocked: LiveData<Boolean> = _isAvatarUnlocked

    fun postMemo(userNovelId: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                ServicePool.memoService.postMemo(
                    userNovelId, MemoWriteRequest(_memoContent.toString())
                )
            }.onSuccess { response ->
                Log.d("postmemo", response.toString())
            }.onFailure {
                Log.d("postmemo", "fail")
            }
        }
    }

    fun patchMemo(memoId: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                ServicePool.memoService.patchNovel(
                    memoId,
                    MemoWriteRequest(_memoContent.toString())
                )
            }.onSuccess {
                Log.d("fatchMemo", "성공")
            }.onFailure {
                Log.d("fatchMemo", "실패")
            }
        }
    }
}