package com.teamwss.websoso.ui.main.record

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwss.websoso.data.ServicePool
import com.teamwss.websoso.ui.main.record.model.Memo
import kotlinx.coroutines.launch

class RecordViewModel : ViewModel() {
    private val _memoCount = MutableLiveData<Long>()
    val memoCount: LiveData<Long> get() = _memoCount

    private val _memos = MutableLiveData<List<Memo>>()
    val memos: LiveData<List<Memo>> get() = _memos

    var memoId: Long = INITIAL_ID
        private set

    private val _userMemoId = MutableLiveData<Long>()
    val userMemoId: LiveData<Long> get() = _userMemoId

    init {
        updateMemo()
    }

    private fun updateMemo() {
        viewModelScope.launch {
            runCatching {
                ServicePool.memoService.getRecord(
                    lastUserNovelId = memoId,
                    size = SIZE,
                    sortType = "NEWEST"
                )
            }.onSuccess { result ->
                _memos.value = result.memos.map {
                    Memo(
                        novelId = it.memoId,
                        novelDate = it.memoDate,
                        novelTitle = it.novelTitle,
                        novelContent = it.memoContent
                    )
                }
                val lastMemo = result.memos.lastOrNull()
                memoId = lastMemo?.memoId ?: 0
                _memoCount.value = result.memoCount
            }.onFailure {
            }
        }
    }

    companion object {
        private const val INITIAL_ID: Long = 99999
        private const val SIZE: Int = 30
    }
}


