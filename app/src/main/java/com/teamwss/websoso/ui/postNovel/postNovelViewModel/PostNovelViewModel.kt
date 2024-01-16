package com.teamwss.websoso.ui.postNovel.postNovelViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwss.websoso.data.ServicePool
import com.teamwss.websoso.data.remote.request.NovelPostRequest
import com.teamwss.websoso.data.remote.response.NovelPlatformPostResponse
import com.teamwss.websoso.ui.common.model.Platforms
import com.teamwss.websoso.ui.common.model.ReadStatus
import com.teamwss.websoso.ui.postNovel.postNovelModel.PostNovelInfoModel
import kotlinx.coroutines.launch
import java.time.LocalDate

class PostNovelViewModel : ViewModel() {

    private val _isNovelAlreadyPosted = MutableLiveData<Boolean>()
    val isNovelAlreadyPosted: LiveData<Boolean> get() = _isNovelAlreadyPosted
    private val _novelInfo = MutableLiveData<PostNovelInfoModel>()
    val novelInfo: LiveData<PostNovelInfoModel> get() = _novelInfo
    private val _readStatus = MutableLiveData<String>()
    val readStatus: LiveData<String> get() = _readStatus
    private val _startDate = MutableLiveData<String?>()
    val startDate: LiveData<String?> get() = _startDate
    private val _endDate = MutableLiveData<String?>()
    val endDate: LiveData<String?> get() = _endDate
    private val _selectedEndDate: MutableLiveData<String> =
        MutableLiveData(LocalDate.now().toString())
    val selectedEndDate: LiveData<String> get() = _selectedEndDate
    private val _selectedStartDate: MutableLiveData<String> =
        MutableLiveData(LocalDate.now().toString())
    val selectedStartDate: LiveData<String> get() = _selectedStartDate
    private val _maxDayValue = MutableLiveData<Int>()
    val maxDayValue: LiveData<Int> get() = _maxDayValue
    private val _rating: MutableLiveData<Float?> = MutableLiveData(0f)
    val rating: LiveData<Float?> get() = _rating
    private val _isDialogShown = MutableLiveData<Int>()
    val isDialogShown: LiveData<Int> get() = _isDialogShown
    private val _isNumberPickerStartSelected = MutableLiveData<Boolean>()
    val isNumberPickerStartSelected: LiveData<Boolean> get() = _isNumberPickerStartSelected
    private val _isNumberPickerDateValid = MutableLiveData<Boolean>()
    val isNumberPickerDateValid: LiveData<Boolean> get() = _isNumberPickerDateValid
    private val _isStartDateVisible = MutableLiveData<Boolean>()
    val isStartDateVisible: LiveData<Boolean> get() = _isStartDateVisible
    private val _isEndDateVisible = MutableLiveData<Boolean>()
    val isEndDateVisible: LiveData<Boolean> get() = _isEndDateVisible
    private val _platforms = MutableLiveData<List<NovelPlatformPostResponse>>()
    val platforms: LiveData<List<NovelPlatformPostResponse>> get() = _platforms
    private val _naverUrl = MutableLiveData<String>("")
    val naverUrl: LiveData<String> get() = _naverUrl
    private val _kakaoUrl = MutableLiveData<String>("")
    val kakaoUrl: LiveData<String> get() = _kakaoUrl
    private val _isServerError = MutableLiveData<Boolean>()
    val isServerError: LiveData<Boolean> get() = _isServerError

    fun fetchUserNovelInfo(novelId: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                ServicePool.userNovelService.getEditNovelInfo(novelId)
            }.onSuccess {
                initUserNovelInfo(it.toUI())
                _isServerError.value = false
                _isNovelAlreadyPosted.value = true
            }.onFailure {
                _isNovelAlreadyPosted.value = false
            }
        }
    }

    fun fetchDefaultNovelInfo(novelId: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                ServicePool.novelService.getPostNovelInfo(novelId)
            }.onSuccess {
                initUserNovelInfo(it.toUI())
                _isServerError.value = false
                _isNovelAlreadyPosted.value = false
            }.onFailure {
                _isServerError.value = true
            }
        }
    }

    fun saveNovelInfo(novelId: Long, request: NovelPostRequest) {
        viewModelScope.launch {
            kotlin.runCatching {
                ServicePool.novelService.postPostNovelInfo(novelId, request)
            }.onSuccess {
                _isServerError.value = false
            }.onFailure {
                _isServerError.value = true
            }
        }
    }

    fun saveUserNovelInfo(novelId: Long, request: NovelPostRequest) {
        viewModelScope.launch {
            kotlin.runCatching {
                ServicePool.userNovelService.patchPostNovelInfo(novelId, request)
            }.onSuccess {
                _isServerError.value = false
            }.onFailure {
                _isServerError.value = true
            }
        }
    }

    private fun initUserNovelInfo(novelInfo: PostNovelInfoModel) {
        _novelInfo.value = novelInfo
        _readStatus.value = novelInfo.readStatus
        _startDate.value = novelInfo.readStartDate ?: LocalDate.now().toString()
        _endDate.value = novelInfo.readEndDate ?: LocalDate.now().toString()
        _rating.value = novelInfo.rating ?: 0f
        setPlatforms(novelInfo.platforms)
    }

    fun updateReadStatus(readStatus: String) {
        _readStatus.value = readStatus
    }

    fun updateReadDate(startDate: String?, endDate: String?) {
        _startDate.value = startDate
        _endDate.value = endDate
    }

    fun updateSelectedDate(selectedStartDate: String, selectedEndDate: String) {
        _selectedStartDate.value = selectedStartDate
        _selectedEndDate.value = selectedEndDate
    }

    fun updateRating(novelRating: Float) {
        _rating.value = novelRating
    }

    fun updateIsDialogShown(isShown: Boolean = false) {
        val visible = 0
        val gone = 8

        if (isShown) _isDialogShown.value = visible
        else _isDialogShown.value = gone
    }

    fun updateIsNumberPickerStartSelected(isSelected: Boolean = true) {
        _isNumberPickerStartSelected.value = isSelected
    }

    private fun formatDateToLocalDate(date: String): LocalDate {
        return LocalDate.of(
            formatToYear(date),
            formatToMonth(date),
            formatToDay(date),
        )
    }

    fun formatToYear(date: String): Int {
        return date.split("-")[0].toInt()
    }

    fun formatToMonth(date: String): Int {
        return date.split("-")[1].toInt()
    }

    fun formatToDay(date: String): Int {
        return date.split("-")[2].toInt()
    }

    fun updateIsDateValid() {
        fun isSelectedDateAfterToday(date: String): Boolean {
            val parsedDate = formatDateToLocalDate(date)
            return parsedDate.isAfter(LocalDate.now())
        }

        fun isStartDateAfterEndDate(): Boolean {
            return formatDateToLocalDate(_selectedStartDate.value.toString()).isAfter(
                formatDateToLocalDate(_selectedEndDate.value.toString())
            )
        }

        _isNumberPickerDateValid.value = when (_readStatus.value) {
            ReadStatus.READING.toString() -> !isSelectedDateAfterToday(_selectedStartDate.value.toString())
            ReadStatus.DROP.toString() -> !isSelectedDateAfterToday(_selectedEndDate.value.toString())
            else -> !isSelectedDateAfterToday(_selectedStartDate.value.toString()) &&
                    !isSelectedDateAfterToday(_selectedEndDate.value.toString()) &&
                    !isStartDateAfterEndDate()
        }
    }

    fun updateIsDateVisible(isStartDateVisible: Boolean = true, isEndDateVisible: Boolean = true) {
        _isStartDateVisible.value = isStartDateVisible
        _isEndDateVisible.value = isEndDateVisible
    }

    fun setDayMaxValue(year: Int, month: Int) {
        _maxDayValue.value = when (month) {
            FEBRUARY -> if (isLeapYear(year)) DAYS_IN_FEBRUARY_IN_LEAP_YEAR else DAYS_IN_FEBRUARY_IN_NON_LEAP_YEAR
            APRIL, JUNE, SEPTEMBER, NOVEMBER -> DAYS_IN_SMALL_MONTH
            else -> DAYS_IN_LARGE_MONTH
        }
    }

    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }

    fun setupAnotherDateValid() {
        when (_readStatus.value) {
            ReadStatus.READING.toString() -> {
                updateSelectedDate(
                    _selectedStartDate.value!!,
                    _selectedStartDate.value!!
                )
            }

            ReadStatus.DROP.toString() -> {
                updateSelectedDate(
                    _selectedEndDate.value!!,
                    _selectedEndDate.value!!
                )
            }
        }
    }

    fun formatDate(year: Int, month: Int, day: Int): String {
        val formattedYear = String.format("%04d", year)
        val formattedMonth = String.format("%02d", month)
        val formattedDay = String.format("%02d", day)
        return "$formattedYear-$formattedMonth-$formattedDay"
    }

    private fun setPlatforms(list: List<NovelPlatformPostResponse>) {
        _platforms.value = list
        list.forEach {
            when (it.platformName) {
                Platforms.NAVER_SERIES.platformName -> _naverUrl.value = it.platformUrl
                Platforms.KAKAO_PAGE.platformName -> _kakaoUrl.value = it.platformUrl
            }
        }
    }

    companion object {
        private const val FEBRUARY = 2
        private const val APRIL = 4
        private const val JUNE = 6
        private const val SEPTEMBER = 9
        private const val NOVEMBER = 11
        private const val DAYS_IN_FEBRUARY_IN_LEAP_YEAR = 29
        private const val DAYS_IN_FEBRUARY_IN_NON_LEAP_YEAR = 28
        private const val DAYS_IN_SMALL_MONTH = 30
        private const val DAYS_IN_LARGE_MONTH = 31
    }
}