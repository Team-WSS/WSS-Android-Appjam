package com.teamwss.websoso.ui.novelDetail

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwss.websoso.R
import com.teamwss.websoso.data.ServicePool
import com.teamwss.websoso.data.remote.response.NovelMemoInfoResponse
import com.teamwss.websoso.data.remote.response.NovelMemoResponse
import com.teamwss.websoso.data.remote.response.NovelPlatformInfoResponse
import com.teamwss.websoso.ui.common.model.ReadStatus
import kotlinx.coroutines.launch

class NovelDetailViewModel : ViewModel() {
    private val _userNovelMemoInfoResponse: MutableLiveData<NovelMemoInfoResponse> =
        MutableLiveData()
    val userNovelMemoInfoResponse: LiveData<NovelMemoInfoResponse> = _userNovelMemoInfoResponse

    private val _userNovelId: MutableLiveData<Long> = MutableLiveData()
    val userNovelId: LiveData<Long> = _userNovelId

    private val _memos: MutableLiveData<List<NovelMemoResponse>> = MutableLiveData()
    val memos: LiveData<List<NovelMemoResponse>> = _memos

    private val _platforms: MutableLiveData<List<NovelPlatformInfoResponse>> = MutableLiveData()
    val platforms: LiveData<List<NovelPlatformInfoResponse>> = _platforms

    private val _isDateNull: MutableLiveData<Boolean> = MutableLiveData()
    val isDateNull: LiveData<Boolean> = _isDateNull

    private val _readStatus = MutableLiveData<ReadStatus>()
    val readStatus: LiveData<ReadStatus> = _readStatus

    private val _readStatusText = MutableLiveData<String>()
    val readStatusText: LiveData<String> = _readStatusText

    private val _readStatusImage = MutableLiveData<Int>()
    val readStatusImage: LiveData<Int> = _readStatusImage

    private val _readDateText = MutableLiveData<String>()
    val readDateText: LiveData<String> = _readDateText

    private val _readDateTildeVisibility = MutableLiveData<Int>()
    val readDateTildeVisibility: LiveData<Int> = _readDateTildeVisibility

    fun getUserNovelMemoInfo(userNovelId: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                ServicePool.userNovelService.getUserNovelMemoInfo(userNovelId)
            }.onSuccess { response ->
                _userNovelMemoInfoResponse.value = response
                _memos.value = response.memos
                _platforms.value = response.platforms
                validateStartEndDateNull()
                updateReadStatus(ReadStatus.valueOf(response.userNovelReadStatus))
            }.onFailure { throwable ->
                Log.e("tongsin", throwable.toString())
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

    private fun validateStartEndDateNull() {
        _isDateNull.value = !(
                _userNovelMemoInfoResponse.value?.userNovelReadStartDate.isNullOrEmpty() &&
                        _userNovelMemoInfoResponse.value?.userNovelReadEndDate.isNullOrEmpty()
                )
    }

    private fun updateReadStatus(status: ReadStatus) {
        _readStatusText.value = when (status) {
            ReadStatus.FINISH -> "읽음"
            ReadStatus.READING -> "읽는 중"
            ReadStatus.DROP -> "하차"
            ReadStatus.WISH -> "읽고 싶음"
        }
        _readStatusImage.value = when (status) {
            ReadStatus.FINISH -> R.drawable.ic_status_finish
            ReadStatus.READING -> R.drawable.ic_status_reading
            ReadStatus.DROP -> R.drawable.ic_status_drop
            ReadStatus.WISH -> R.drawable.ic_status_wish
        }
        _readDateText.value = when (status) {
            ReadStatus.FINISH -> "읽은 날짜"
            ReadStatus.READING -> "시작 날짜"
            ReadStatus.DROP -> "종료 날짜"
            else -> null
        }
        val isVisible = status != ReadStatus.WISH
        _readDateTildeVisibility.value =
            if (status == ReadStatus.FINISH) View.VISIBLE else View.GONE
    }
}