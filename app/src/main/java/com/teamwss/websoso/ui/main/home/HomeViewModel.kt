package com.teamwss.websoso.ui.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.teamwss.websoso.App
import com.teamwss.websoso.data.model.RepresentiveAvatarEntity
import com.teamwss.websoso.data.model.SosoPickNovelEntity
import com.teamwss.websoso.data.repository.AvatarRepository
import com.teamwss.websoso.data.repository.UserNovelsRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userNovelsRepository: UserNovelsRepository,
    private val avatarRepository: AvatarRepository
) : ViewModel() {
    private var _sosopickNovels: MutableLiveData<List<SosoPickNovelEntity>> = MutableLiveData(sosoPickInitData)
    val sosopickNovels: LiveData<List<SosoPickNovelEntity>>
        get() = _sosopickNovels

    private var _representativeAvatar: MutableLiveData<RepresentiveAvatarEntity> = MutableLiveData(representativeAvatarInitData)
    val representativeAvatar: LiveData<RepresentiveAvatarEntity>
        get() = _representativeAvatar

    init {
        getSosoPickNovels()
        getRepresentativeAvatar()
    }

    private fun getSosoPickNovels() {
        viewModelScope.launch {
            runCatching {
                userNovelsRepository.getSosoPickNovels()
            }.onSuccess {
                _sosopickNovels.value = it
            }.onFailure {
                Log.e("HomeViewModel", it.message ?: "error")
            }
        }
    }

    private fun getRepresentativeAvatar() {
        viewModelScope.launch {
            runCatching {
                avatarRepository.getRepresentativeAvatar()
            }.onSuccess {
                _representativeAvatar.value = it
            }.onFailure {
                Log.e("HomeViewModel", it.message ?: "error")
            }
        }
    }

    companion object {
        private val sosoPickInitData = listOf(
            SosoPickNovelEntity(0, "", "", 0, ""),
            SosoPickNovelEntity(0, "", "", 0, ""),
            SosoPickNovelEntity(0, "", "", 0, ""),
            SosoPickNovelEntity(0, "", "", 0, ""),
        )
        private val representativeAvatarInitData = RepresentiveAvatarEntity(
            avatarId = 0,
            userNickname = "",
            avatarLine = "",
            avatarTag = ""
        )
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val userNovelsRepository = App.getUserNovelsRepository()
                val avatarRepository = App.getAvatarRepository()
                HomeViewModel(
                    userNovelsRepository = userNovelsRepository,
                    avatarRepository = avatarRepository
                )
            }
        }
    }
}

