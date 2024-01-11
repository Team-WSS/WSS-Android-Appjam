package com.teamwss.websoso.ui.postNovel.postNovelViewModel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamwss.websoso.util.loadBlurredImage
import com.teamwss.websoso.util.loadCoverImage
import java.time.LocalDate

class PostNovelViewModel : ViewModel() {

    private val _readStatus = MutableLiveData<String>()
    val readStatus: LiveData<String> get() = _readStatus
    private val _startDate = MutableLiveData<String>()
    val startDate: LiveData<String> get() = _startDate
    private val _selectedStartDate: MutableLiveData<String> =
        MutableLiveData(LocalDate.now().toString())
    val selectedStartDate: LiveData<String> get() = _selectedStartDate
    private val _endDate = MutableLiveData<String>()
    val endDate: LiveData<String> get() = _endDate
    private val _selectedEndDate: MutableLiveData<String> =
        MutableLiveData(LocalDate.now().toString())
    val selectedEndDate: LiveData<String> get() = _selectedEndDate
    private val _rating = MutableLiveData<Float>()
    val rating: LiveData<Float> get() = _rating
    private val _isDialogShown = MutableLiveData<Int>()
    val isDialogShown: LiveData<Int> get() = _isDialogShown
    private val _dummyData = MutableLiveData<DummyData>()
    val dummyData: LiveData<DummyData> get() = _dummyData
    private val _isStartSelected = MutableLiveData<Boolean>()
    val isStartSelected: LiveData<Boolean> get() = _isStartSelected
    private val _isDateValid = MutableLiveData<Boolean>()
    val isDateValid: LiveData<Boolean> get() = _isDateValid
    private val _isStartDateVisible = MutableLiveData<Boolean>()
    val isStartDateVisible: LiveData<Boolean> get() = _isStartDateVisible
    private val _isEndDateVisible = MutableLiveData<Boolean>()
    val isEndDateVisible: LiveData<Boolean> get() = _isEndDateVisible

    fun getUserNovelInfo() {
        _dummyData.value = DummyData(
            novelId = 1,
            userNovelTitle = "노 게임 노 라이프",
            userNovelAuthor = "카미야 유우",
            userNovelGenre = "라이트노벨",
            userNovelImg = "https://i.namu.wiki/i/j1S3TlFyve1UjbCnzF_g6qEgFnMi8usZ_DLCn8lP91FwgpPgwkv_GNCD2fmu5uEPgPU5CSdzDF5qwe_8Ains2UzdgGgI-bzT95MQeBrceU9E7Hr26fWBFREMLDGiZm01VtAXHgXRO9kviGz3sYwQ-w.webp",
            userNovelDescription = "백수에 골방지기지만 인터넷에서는 도시전설이라는 이야기마저 떠도는 천재 게이머 남매, 소라(空)와 시로(白). 둘이 합쳐 하나인 『　　』(공백)인 남매는 세상을 「쓰레기 게임」이라 부르며 지내던 어느 날, 『신』을 자칭하는 소년에게 이끌려 이세계로 소환된다. 그곳은 신에 의해 전쟁이 금지되었으며, 『모든 것』── 「국경선마저도 게임으로 결판이 나는」 세계였다.\n\n다른 종족들에게 연패를 거듭해 마지막 도시 하나만을 남겨둔 인류. 소라와 시로 폐인남매가 이세계에서는 ‘인류의 구세주’가 될 수 있을까?",
            userNovelRating = 5.0f,
            userNovelReadStatus = "READING",
            readStartDate = "2023-12-26",
            readEndDate = "2023-12-26",
            platforms = listOf(
                DummyData.Platform("네이버시리즈", "https://series.naver.com/"),
                DummyData.Platform("카카오페이지", "https://page.kakao.com"),
            ),
        )
    }


    fun updateReadStatus(status: String) {
        _readStatus.value = status
    }

    fun updateReadDate(startDate: String, endDate: String) {
        _startDate.value = startDate
        _endDate.value = endDate
    }

    fun updateSelectedDate(selectedStartDate: String, selectedEndDate: String) {
        _selectedStartDate.value = selectedStartDate
        _selectedEndDate.value = selectedEndDate
    }

    fun updateRating(rating: Float) {
        _rating.value = rating
    }

    fun updateIsDialogShown(isShown: Boolean = false) {
        if (isShown) _isDialogShown.value = 0
        else _isDialogShown.value = 8
    }

    fun updateIsStartSelected(isSelected: Boolean = true) {
        _isStartSelected.value = isSelected
    }

    private fun splitDate(date: String): LocalDate {
        return LocalDate.of(
            date.split("-")[0].toInt(),
            date.split("-")[1].toInt(),
            date.split("-")[2].toInt()
        )
    }
    fun updateIsDateValid() {
        _isDateValid.value = !splitDate(_selectedStartDate.value.toString()).isAfter(splitDate(_selectedEndDate.value.toString()))
    }

    fun updateIsDateVisible(isStartDateVisible : Boolean = true, isEndDateVisible : Boolean = true) {
        _isStartDateVisible.value = isStartDateVisible
        _isEndDateVisible.value = isEndDateVisible
    }
}

data class DummyData(

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