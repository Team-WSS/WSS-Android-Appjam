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
import com.teamwss.websoso.data.mapper.toData
import com.teamwss.websoso.data.model.LibraryUserNovelEntity
import com.teamwss.websoso.data.remote.request.UserNovelsRequest
import com.teamwss.websoso.data.repository.UserNovelsRepository
import com.teamwss.websoso.ui.main.library.model.ReadState
import com.teamwss.websoso.ui.main.library.model.SortType
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val userNovelsRepository: UserNovelsRepository
) : ViewModel() {

    init {
        getNovels(ReadState.ALL)
    }

    private var _readState: MutableLiveData<ReadState> = MutableLiveData(ReadState.ALL)
    val readState: LiveData<ReadState>
        get() = _readState

    private var _lastReadState: MutableLiveData<ReadState> = MutableLiveData(ReadState.ALL)
    val lastReadState: LiveData<ReadState>
        get() = _lastReadState

    private var _currentUserNovels: MutableLiveData<List<LibraryUserNovelEntity>> =
        MutableLiveData()
    val currentUserNovels: LiveData<List<LibraryUserNovelEntity>>
        get() = _currentUserNovels

    private var _currentSortType : MutableLiveData<SortType> = MutableLiveData(SortType.NEWEST)
    val currentSortType : LiveData<SortType>
        get() = _currentSortType

    fun setReadState(readState: ReadState) {
        if (this.readState.value != readState) {
            _readState.value = readState
            getNovels(readState)
        }
    }

    fun getNovels(readState: ReadState) {
        viewModelScope.launch {
            runCatching {
                userNovelsRepository.getNovels(
                    UserNovelsRequest(
                        readState.toString(),
                        0,
                        USER_NOVEL_COUNT,
                        currentSortType.value.toString()
                    )
                )
            }.onSuccess {
                _currentUserNovels.value = it.userNovels.toData()
            }.onFailure {
                Log.e("LibraryViewModel", it.message ?: "error")
            }
        }
    }

    companion object {
        const val USER_NOVEL_COUNT = 20

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val userNovelsRepository = App.userNovelsRepository
                LibraryViewModel(
                    userNovelsRepository = userNovelsRepository
                )
            }
        }
    }

}