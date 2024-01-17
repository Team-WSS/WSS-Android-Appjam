package com.teamwss.websoso.ui.main.library

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.teamwss.websoso.App
import com.teamwss.websoso.data.mapper.UserNovelMapper.toData
import com.teamwss.websoso.data.model.LibraryUserNovelEntity
import com.teamwss.websoso.data.repository.UserNovelsRepository
import com.teamwss.websoso.ui.main.library.model.ReadState
import com.teamwss.websoso.ui.main.library.model.SortType
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val userNovelsRepository: UserNovelsRepository
) : ViewModel() {

    private var _readState: MutableLiveData<ReadState> = MutableLiveData(ReadState.ALL)
    val readState: LiveData<ReadState>
        get() = _readState

    private var _lastReadState: MutableLiveData<ReadState> = MutableLiveData(ReadState.ALL)
    val lastReadState: LiveData<ReadState>
        get() = _lastReadState

    private var _currentUserNovels: MutableLiveData<List<LibraryUserNovelEntity>> =
        MutableLiveData(emptyList())
    val currentUserNovels: LiveData<List<LibraryUserNovelEntity>>
        get() = _currentUserNovels

    private var _currentSortType: MutableLiveData<SortType> = MutableLiveData(SortType.NEWEST)
    val currentSortType: LiveData<SortType>
        get() = _currentSortType

    private var _userNovelCount: MutableLiveData<Long> = MutableLiveData(0)
    val userNovelCount: LiveData<Long>
        get() = _userNovelCount

    init {
        _currentSortType.value = SortType.NEWEST
        getNovels(ReadState.ALL)
    }

    fun setReadState(readState: ReadState) {
        if (this.readState.value != readState) {
            _lastReadState.value = this.readState.value
            _readState.value = readState
            getNovels(readState)
        }
    }

    fun getNovels(readState: ReadState) {
        viewModelScope.launch {
            runCatching {
                userNovelsRepository.getUserNovels(
                    readState.toString(),
                    setupLastUserId(),
                    USER_NOVEL_COUNT,
                    currentSortType.value.toString()
                )
            }.onSuccess {(userNovels, userNovelCount) ->
                _currentUserNovels.value = userNovels
                _userNovelCount.value = userNovelCount
            }.onFailure {
                Log.e("LibraryViewModel", it.message ?: "error")
            }
        }
    }

    private fun setupLastUserId() = when (currentSortType.value) {
        SortType.NEWEST -> NEWEST_MAXIMUM_ID
        SortType.OLDEST -> OLDEST_MINIMUM_ID
        else -> NEWEST_MAXIMUM_ID
    }

    companion object {
        const val USER_NOVEL_COUNT = 20
        const val NEWEST_MAXIMUM_ID = 9999L
        const val OLDEST_MINIMUM_ID = 0L

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val userNovelsRepository = App.getUserNovelsRepository()
                LibraryViewModel(
                    userNovelsRepository = userNovelsRepository
                )
            }
        }
    }

}