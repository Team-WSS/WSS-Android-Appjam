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
import com.teamwss.websoso.data.model.LibraryUserNovelEntity
import com.teamwss.websoso.data.repository.UserNovelsRepository
import com.teamwss.websoso.ui.main.library.model.ReadState
import com.teamwss.websoso.ui.main.library.model.SortType
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val userNovelsRepository: UserNovelsRepository
) : ViewModel() {

    private var _currentReadState: MutableLiveData<ReadState> = MutableLiveData(ReadState.ALL)
    val currentReadState: LiveData<ReadState>
        get() = _currentReadState

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

    private var _libraryUiState: MutableLiveData<LibraryUiState> =
        MutableLiveData(LibraryUiState.Uninitialized)
    val libraryUiState: LiveData<LibraryUiState>
        get() = _libraryUiState

    fun setReadState(readState: ReadState) {
        if (this.currentReadState.value != readState) {
            _currentReadState.value = readState
            _libraryUiState.value = LibraryUiState.Resumed
        }
    }

    fun updateUiStateResumed() {
        // 최초 시작 시에 Resumed 상태로 변경 되는 것을 방지합니다.
        if (_libraryUiState.value != LibraryUiState.Loading)
            _libraryUiState.value = LibraryUiState.Resumed
    }

    fun getNovels() {
        _libraryUiState.value = LibraryUiState.Loading
        viewModelScope.launch {
            runCatching {
                userNovelsRepository.getUserNovels(
                    currentReadState.value.toString(),
                    setupLastUserId(),
                    NOVEL_GET_SIZE,
                    currentSortType.value.toString()
                )
            }.onSuccess { (userNovels, userNovelCount) ->
                _currentUserNovels.value = userNovels
                _userNovelCount.value = userNovelCount
                _libraryUiState.value = LibraryUiState.Success
            }.onFailure {
                _libraryUiState.value = LibraryUiState.Error
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
        const val NOVEL_GET_SIZE = 20
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