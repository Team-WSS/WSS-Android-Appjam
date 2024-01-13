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
import com.teamwss.websoso.data.model.LibraryUserNovel
import com.teamwss.websoso.data.remote.request.UserNovelsRequest
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

    private var _currentUserNovels: MutableLiveData<List<LibraryUserNovel>> = MutableLiveData()
    val currentUserNovels: LiveData<List<LibraryUserNovel>>
        get() = _currentUserNovels

    fun setReadState(readState: ReadState) {
        _readState.value = readState
    }

    fun getNovels() {
        viewModelScope.launch {
            runCatching {
               userNovelsRepository.getNovels(
                   UserNovelsRequest(
                       ReadState.ALL.toString(),
                       0,
                       10,
                       SortType.OLDEST.toString()
                   )
               )
            }.onSuccess {
                _currentUserNovels.value = it.userNovelList.toData()
            }.onFailure {
                Log.e("LibraryViewModel", it.message ?: "error")
            }
        }
    }

    companion object {
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