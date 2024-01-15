package com.teamwss.websoso.ui.search.searchViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    private val _isNovelAlreadyPosted = MutableLiveData<Boolean>()
    val isNovelAlreadyPosted: LiveData<Boolean> get() = _isNovelAlreadyPosted

}