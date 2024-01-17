package com.teamwss.websoso.ui.memoWrite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwss.websoso.data.ServicePool
import com.teamwss.websoso.data.remote.request.MemoWriteRequest
import kotlinx.coroutines.launch

class MemoWriteViewModel : ViewModel() {
    private var _memoContent: MutableLiveData<String?> = MutableLiveData("")
    val memoContent: MutableLiveData<String?> = _memoContent

    private var initialMemoContent: String? = null

    private var _isChanged: MutableLiveData<Boolean> = MutableLiveData(false)
    val isChanged: LiveData<Boolean> = _isChanged

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

    private var _isMemoPosted: MutableLiveData<Boolean> = MutableLiveData()
    val isMemoPosted: LiveData<Boolean> = _isMemoPosted

    private var _isMemoPatched: MutableLiveData<Boolean> = MutableLiveData()
    val isMemoPatched: LiveData<Boolean> = _isMemoPatched

    fun postMemo(userNovelId: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                ServicePool.memoService.postMemo(
                    userNovelId, MemoWriteRequest(_memoContent.value.toString())
                )
            }.onSuccess { response ->
                _isMemoPosted.value = true
                _isAvatarUnlocked.value = response.isAvatarUnlocked
            }.onFailure {
                _isMemoPosted.value = false
            }
        }
    }

    fun patchMemo(memoId: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                ServicePool.memoService.patchNovel(
                    memoId,
                    MemoWriteRequest(_memoContent.value.toString())
                )
            }.onSuccess {
                _isMemoPatched.value = true
            }.onFailure {
                _isMemoPatched.value = false
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
        if (initialMemoContent == null) {
            initialMemoContent = memoContent
        }
        _memoContent.value = memoContent
        checkIfContentChanged()
    }

    fun updateMemoContent(memoContent: String) {
        _memoContent.value = memoContent
        checkIfContentChanged()
    }

    private fun checkIfContentChanged() {
        val currentContent = _memoContent.value.orEmpty()
        _isChanged.value = currentContent != initialMemoContent || currentContent.isEmpty()
    }
}