package com.teamwss.websoso

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.teamwss.websoso.data.NetworkModule
import com.teamwss.websoso.data.local.WebsosoLocalStorage
import com.teamwss.websoso.data.remote.service.LibraryService
import com.teamwss.websoso.data.repository.UserNovelsRepository

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        userPrefs = WebsosoLocalStorage.getInstance(this)

        val libraryService = NetworkModule.create<LibraryService>()
        userNovelsRepository = UserNovelsRepository(libraryService)
    }

    companion object {
        lateinit var userNovelsRepository: UserNovelsRepository
            private set
        lateinit var userPrefs: WebsosoLocalStorage
            private set
    }
}