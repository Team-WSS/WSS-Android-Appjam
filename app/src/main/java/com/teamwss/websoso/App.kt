package com.teamwss.websoso

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.teamwss.websoso.data.NetworkModule
import com.teamwss.websoso.data.local.WebsosoLocalStorage
import com.teamwss.websoso.data.remote.service.AuthService
import com.teamwss.websoso.data.remote.service.AvatarService
import com.teamwss.websoso.data.remote.service.UserNovelService
import com.teamwss.websoso.data.repository.AuthRepository
import com.teamwss.websoso.data.repository.AvatarRepository
import com.teamwss.websoso.data.repository.UserNovelsRepository

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    companion object {
        lateinit var applicationContext: Context
            private set

        fun getWebsosoLocalStorage(): WebsosoLocalStorage {
            return WebsosoLocalStorage.getInstance(applicationContext)
        }

        fun getUserNovelsRepository(): UserNovelsRepository {
            val userNovelService: UserNovelService = NetworkModule.create<UserNovelService>()

            return UserNovelsRepository(userNovelService)
        }

        fun getAvatarRepository(): AvatarRepository {
            val avatarService: AvatarService = NetworkModule.create<AvatarService>()

            return AvatarRepository(avatarService)
        }

        fun getAuthRepository(): AuthRepository {
            val authService: AuthService = NetworkModule.create<AuthService>()

            return AuthRepository(authService)
        }
    }
}