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
    private var _memoContent: MutableLiveData<String?> = MutableLiveData()
    val memoContent: MutableLiveData<String?> = _memoContent

    private var _isAvatarUnlocked: MutableLiveData<Boolean> = MutableLiveData()
    val isAvatarUnlocked: LiveData<Boolean> = _isAvatarUnlocked

    private var _memoId: MutableLiveData<Long> = MutableLiveData()
    val memoId: LiveData<Long> = _memoId

    private var _userNovelId: MutableLiveData<Long> = MutableLiveData()
    val userNovelId: LiveData<Long> = _userNovelId

    private var _userNovelTitle: MutableLiveData<String> = MutableLiveData()
    val userNovelTitle: LiveData<String> = _userNovelTitle

    private var _userNovelAuthor: MutableLiveData<String> = MutableLiveData()
    val userNovelAuthor: LiveData<String> = _userNovelAuthor

    private var _userNovelImage: MutableLiveData<String> = MutableLiveData()
    val userNovelImage: LiveData<String> = _userNovelImage

    init {
        _memoContent.value = ""
    }

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

    fun updateUserNovelData(
        memoId: Long,
        userNovelId: Long,
        userNovelTitle: String,
        userNovelAuthor: String,
        userNovelImage: String
    ) {
        _memoId.value = memoId
        _userNovelId.value = userNovelId
        _userNovelTitle.value = userNovelTitle
        _userNovelAuthor.value = userNovelAuthor
        _userNovelImage.value = userNovelImage
    }

    fun getMemoContent(memoContent: String) {
        _memoContent.value = memoContent
    }

    fun updateMemoContent(memoContent: String) {
        _memoContent.value = memoContent
    }
}