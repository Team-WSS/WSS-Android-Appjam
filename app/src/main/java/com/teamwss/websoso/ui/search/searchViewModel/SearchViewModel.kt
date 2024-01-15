package com.teamwss.websoso.ui.search.searchViewModel

import android.util.Log
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

    fun searchNovels(lastNovelId: Long, size: Int, word: String) {
        _isLoading.value = true
        viewModelScope.launch {
            kotlin.runCatching {
                ServicePool.novelService.getNovels(lastNovelId, size, word)
            }.onSuccess {
                val oldNovels = _searchResult.value?.novels ?: emptyList()
                val newNovels = it.novels
                _searchResult.value = SearchNovelsResponse(oldNovels + newNovels)
                _isLoading.value = false
                Log.e("SearchViewModelSuccess", "searchNovels: ")
            }.onFailure {
                _isLoading.value = false
                Log.e("SearchViewModelFail", "searchNovels: ", it)
            }
        }
    }
}