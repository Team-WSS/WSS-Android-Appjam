package com.teamwss.websoso

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.teamwss.websoso.data.NetworkModule
import com.teamwss.websoso.data.local.WebsosoLocalStorage
import com.teamwss.websoso.data.remote.service.UserNovelService
import com.teamwss.websoso.data.repository.UserNovelsRepository

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        userPrefs = WebsosoLocalStorage.getInstance(this)

        val userNovelService = NetworkModule.create<UserNovelService>()
        userNovelsRepository = UserNovelsRepository(userNovelService)
    }

    companion object {
        lateinit var userNovelsRepository: UserNovelsRepository
            private set
        lateinit var userPrefs: WebsosoLocalStorage
            private set
    }
}