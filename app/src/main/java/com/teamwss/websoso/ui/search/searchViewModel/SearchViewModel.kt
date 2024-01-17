package com.teamwss.websoso.ui.search.searchViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamwss.websoso.data.ServicePool
import com.teamwss.websoso.data.remote.response.SearchNovelsResponse
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _searchResult = MutableLiveData<SearchNovelsResponse>()
    val searchResult: LiveData<SearchNovelsResponse> get() = _searchResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _searchWord = MutableLiveData<String>()
    val searchWord: LiveData<String> get() = _searchWord

    fun searchNovels(lastNovelId: Long, size: Int, word: String) {
        if (word != _searchWord.value) _searchResult.value = SearchNovelsResponse(emptyList())

        _isLoading.value = true
        viewModelScope.launch {
            kotlin.runCatching {
                ServicePool.novelService.getNovels(lastNovelId, size, word)
            }.onSuccess {
                val oldNovels = _searchResult.value?.novels ?: emptyList()
                val newNovels = it.novels
                _searchResult.value = SearchNovelsResponse(oldNovels + newNovels)
                _searchWord.value = word
                _isLoading.value = false
            }.onFailure {
                _isLoading.value = false
            }
        }
    }

    companion object {
        const val LAST_NOVEL_ID = 99999999L
        const val PAGE_SIZE = 40
        const val EXTRA_PAGE_SIZE = 20
        const val SEARCH_DELAY = 1000L
    }
}