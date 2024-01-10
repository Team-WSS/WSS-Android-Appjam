package com.teamwss.websoso.ui.postNovel.postNovelViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostNovelViewModel : ViewModel() {

    private val _readStatus = MutableLiveData<String>()
    val readStatus: LiveData<String> get() = _readStatus
    private val _startDate = MutableLiveData<String>()
    val startDate: LiveData<String> get() = _startDate
    private val _endDate = MutableLiveData<String>()
    val endDate: LiveData<String> get() = _endDate

    private val _isDialogShown = MutableLiveData<Boolean>()
    val isDialogShown: LiveData<Boolean> get() = _isDialogShown

    private val dummyData = DummyData()
    fun getUserNovelInfo(): DummyData {
        return dummyData
    }

    fun updateReadStatus(status: String) {
        _readStatus.value = status
    }

    fun updateReadDate(startDate: String, endDate: String) {
        _startDate.value = startDate
        _endDate.value = endDate
    }

    fun updateIsDialogShown(isShown: Boolean = false) {
        _isDialogShown.value = isShown
    }
}

data class DummyData(

    val novelId: Long = 1,
    val userNovelTitle: String = "노 게임 노 라이프",
    val userNovelAuthor: String = "카미야 유우",
    val userNovelGenre: String = "라이트노벨",
    val userNovelImg: String = "https://i.namu.wiki/i/j1S3TlFyve1UjbCnzF_g6qEgFnMi8usZ_DLCn8lP91FwgpPgwkv_GNCD2fmu5uEPgPU5CSdzDF5qwe_8Ains2UzdgGgI-bzT95MQeBrceU9E7Hr26fWBFREMLDGiZm01VtAXHgXRO9kviGz3sYwQ-w.webp",
    val userNovelDescription: String = "백수에 골방지기지만 인터넷에서는 도시전설이라는 이야기마저 떠도는 천재 게이머 남매, 소라(空)와 시로(白). 둘이 합쳐 하나인 『　　』(공백)인 남매는 세상을 「쓰레기 게임」이라 부르며 지내던 어느 날, 『신』을 자칭하는 소년에게 이끌려 이세계로 소환된다. 그곳은 신에 의해 전쟁이 금지되었으며, 『모든 것』── 「국경선마저도 게임으로 결판이 나는」 세계였다.\n\n다른 종족들에게 연패를 거듭해 마지막 도시 하나만을 남겨둔 인류. 소라와 시로 폐인남매가 이세계에서는 ‘인류의 구세주’가 될 수 있을까?",
    val userNovelRating: Float = 5.0f,
    val userNovelReadStatus: String = "읽는 중",
    val readStartDate: String = "2023-12-26",
    val readEndDate: String = "2023-12-26",
    val keywordNames: List<String> = listOf("먼치킨", "이세계"),
    val platforms: List<Platform> = listOf(
        Platform("네이버시리즈", "https://series.naver.com/"),
        Platform("카카오페이지", "https://page.kakao.com")
    ),

    ) {
    data class Platform(
        val platformName: String,
        val platformUrl: String,
    )
}