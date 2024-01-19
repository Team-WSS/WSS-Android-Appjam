package com.teamwss.websoso.ui.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.teamwss.websoso.App
import com.teamwss.websoso.data.local.WebsosoLocalStorage
import com.teamwss.websoso.data.repository.AuthRepository
import kotlinx.coroutines.launch

class SplashViewModel(
    private val authRepository: AuthRepository,
    private val websosoLocalStorage: WebsosoLocalStorage
) : ViewModel() {

    fun postLogin() {
        viewModelScope.launch {
            runCatching {
                authRepository.postLogin(LOGIN_ID)
            }.onSuccess {
                websosoLocalStorage.accessToken = it.Authorization
            }.onFailure {
                Log.e("SplashViewModel", "postLogin Error: ${it.message}")
            }
        }
    }

    companion object {
        const val LOGIN_ID = 1L

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val authRepository: AuthRepository = App.getAuthRepository()
                val websosoLocalStorage: WebsosoLocalStorage = App.getWebsosoLocalStorage()
                SplashViewModel(
                    authRepository = authRepository,
                    websosoLocalStorage = websosoLocalStorage
                )
            }
        }
    }
}