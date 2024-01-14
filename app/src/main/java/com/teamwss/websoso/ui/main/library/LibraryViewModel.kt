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

    override fun onCleared() {
        super.onCleared()
        Log.d("LibraryViewModel", "onCleared")
    }

    init {
        getNovels(ReadState.ALL)
        App.userPrefs.accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MDUxOTM1MTEsImV4cCI6MTcwNjA1NzUxMSwidXNlcklkIjoxfQ.VK42vScpGJ9rpie-jbE2xlGeEbrnP4u6eN8UyzoFbvQ"
        Log.d("LibraryViewModel", "init")
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
                        10,
                        SortType.OLDEST.toString()
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