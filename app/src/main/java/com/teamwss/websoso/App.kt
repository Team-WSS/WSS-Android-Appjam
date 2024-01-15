package com.teamwss.websoso

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.teamwss.websoso.data.NetworkModule
import com.teamwss.websoso.data.local.WebsosoLocalStorage
import com.teamwss.websoso.data.remote.service.AvatarService
import com.teamwss.websoso.data.remote.service.UserNovelService
import com.teamwss.websoso.data.repository.AvatarRepository
import com.teamwss.websoso.data.repository.UserNovelsRepository

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        userPrefs = WebsosoLocalStorage.getInstance(this)

        userNovelsRepository = getUserNovelsRepository()
        avatarRepository = getAvatarRepository()
    }

    private fun getUserNovelsRepository(): UserNovelsRepository {
        val userNovelService: UserNovelService = NetworkModule.create<UserNovelService>()

        return UserNovelsRepository(userNovelService)
    }

    private fun getAvatarRepository(): AvatarRepository {
        val avatarService: AvatarService = NetworkModule.create<AvatarService>()

        return AvatarRepository(avatarService)
    }

    companion object {
        lateinit var userNovelsRepository: UserNovelsRepository
            private set
        lateinit var avatarRepository: AvatarRepository
            private set
        lateinit var userPrefs: WebsosoLocalStorage
            private set
    }
}