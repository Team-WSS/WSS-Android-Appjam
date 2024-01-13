package com.teamwss.websoso.ui.postNovel.postNovelViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwss.websoso.data.ServicePool
import com.teamwss.websoso.data.remote.response.EditNovelResponse
import com.teamwss.websoso.data.remote.response.GetPlatformResponse
import kotlinx.coroutines.launch
import java.time.LocalDate

class PostNovelViewModel : ViewModel() {

    private val _editNovelInfo = MutableLiveData<EditNovelResponse>()
    val editNovelInfo: LiveData<EditNovelResponse> get() = _editNovelInfo
    private val _readStatus = MutableLiveData<String>()
    val readStatus: LiveData<String> get() = _readStatus
    private val _startDate = MutableLiveData<String>()
    val startDate: LiveData<String> get() = _startDate
    private val _endDate = MutableLiveData<String>()
    val endDate: LiveData<String> get() = _endDate
    private val _selectedEndDate: MutableLiveData<String> =
        MutableLiveData(LocalDate.now().toString())
    val selectedEndDate: LiveData<String> get() = _selectedEndDate
    private val _selectedStartDate: MutableLiveData<String> =
        MutableLiveData(LocalDate.now().toString())
    val selectedStartDate: LiveData<String> get() = _selectedStartDate
    private val _maxDayValue = MutableLiveData<Int>()
    val maxDayValue: LiveData<Int> get() = _maxDayValue
    private val _rating = MutableLiveData<Float>()
    val rating: LiveData<Float> get() = _rating

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

    private val _platforms = MutableLiveData<List<GetPlatformResponse>>()
    val platforms: LiveData<List<GetPlatformResponse>> get() = _platforms
    private val _naverUrl = MutableLiveData<String>()
    val naverUrl: LiveData<String> get() = _naverUrl
    private val _kakaoUrl = MutableLiveData<String>()
    val kakaoUrl: LiveData<String> get() = _kakaoUrl

    fun getUserNovelInfo(novelId: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                ServicePool.postNovelService.getEditNovelInfo(novelId)
            }.onSuccess {
                _editNovelInfo.value = it
                Log.e("getUserNovelInfo", "getUserNovelInfo() success: ${it}")
            }.onFailure {
                Log.e("getUserNovelInfo", "getUserNovelInfo() error: ${it.message}")
            }
        }
    }

    fun updateReadStatus(readStatus: String) {
        _readStatus.value = readStatus
    }

    fun updateReadDate(startDate: String, endDate: String) {
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

    private fun splitDateToLocalDate(date: String): LocalDate {
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
        _isNumberPickerDateValid.value =
            !splitDateToLocalDate(_selectedStartDate.value.toString()).isAfter(
                splitDateToLocalDate(_selectedEndDate.value.toString())
            )
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
            ReadStatus.READING.status -> {
                updateSelectedDate(
                    _selectedStartDate.value!!,
                    _selectedStartDate.value!!
                )
            }

            ReadStatus.DROP.status -> {
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

    fun setPlatforms(list: List<GetPlatformResponse>) {
        _platforms.value = list
        list.forEach { platform ->
            when (platform.platformName) {
                NAVER_SERIES -> _naverUrl.value = platform.platformUrl
                KAKAO_PAGE -> _kakaoUrl.value = platform.platformUrl
            }
        }
    }

    companion object {
        const val NAVER_SERIES = "네이버시리즈"
        const val KAKAO_PAGE = "카카오페이지"

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

    enum class ReadStatus(val status: String) {
        FINISH("FINISH"), READING("READING"), DROP("DROP"), WISH("WISH")
    }
}

data class EditResponse(

    val novelId: Long = 0,
    val userNovelTitle: String = "",
    val userNovelAuthor: String = "",
    val userNovelGenre: String = "",
    val userNovelImg: String = "",
    val userNovelDescription: String = "",
    val userNovelRating: Float = 0f,
    val userNovelReadStatus: String = "",
    val readStartDate: String? = LocalDate.now().toString(),
    val readEndDate: String? = LocalDate.now().toString(),
    val platforms: List<Platform> = listOf(),

    ) {
    data class Platform(
        val platformName: String = "",
        val platformUrl: String = "",
    )
}