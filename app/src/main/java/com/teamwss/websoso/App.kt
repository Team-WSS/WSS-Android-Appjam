package com.teamwss.websoso

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.teamwss.websoso.data.NetworkModule
import com.teamwss.websoso.data.repository.UserNovelsRepository

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        userNovelsRepository = UserNovelsRepository(NetworkModule.userNovelsApi)
    }

    companion object {
        lateinit var userNovelsRepository: UserNovelsRepository
    }
}