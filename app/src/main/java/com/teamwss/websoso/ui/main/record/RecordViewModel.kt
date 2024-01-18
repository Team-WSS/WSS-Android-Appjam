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

    init {
        updateMemo()
    }

    fun updateMemo() {
        viewModelScope.launch {
            runCatching {
                ServicePool.memoService.getRecord(
                    INITIAL_ID,
                    size = SIZE,
                    sortType = "NEWEST"
                )
            }.onSuccess { result ->
                _memos.value = result.memos.map {
                    Memo(
                        memoId = it.memoId,
                        memoDate = it.memoDate,
                        novelTitle = it.novelTitle,
                        memoContent = it.memoContent
                    )
                }
                _memoCount.value = result.memoCount
            }.onFailure {
                Log.d("RecordViewModel Error", it.toString())
            }
        }
    }

    companion object {
        private const val INITIAL_ID: Long = 99999
        private const val SIZE: Int = 100
    }
}


