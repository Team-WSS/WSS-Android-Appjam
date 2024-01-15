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

    fun searchNovels(lastNovelId: Long, size: Int, word: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                ServicePool.novelService.getNovels(lastNovelId, size, word)
            }.onSuccess {
                _searchResult.value = it
                Log.e("SearchViewModelSuccess", "searchNovels: ")
            }.onFailure {
                Log.e("SearchViewModelFail", "searchNovels: ", it)
            }
        }
    }
}