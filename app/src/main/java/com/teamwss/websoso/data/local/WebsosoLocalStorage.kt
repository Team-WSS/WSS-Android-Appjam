package com.teamwss.websoso.data.local

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.putString
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.teamwss.websoso.BuildConfig

class WebsosoLocalStorage(context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val pref: SharedPreferences =
        if (BuildConfig.DEBUG) {
            context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        } else {
            EncryptedSharedPreferences.create(
                context,
                FILE_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }

    var accessToken: String?
        get() = pref.getString(ACCESS_TOKEN, null)
        set(value) = pref.edit{ putString(ACCESS_TOKEN, value) }

    var userName: String
        get() = pref.getString(USER_NAME, "") ?: ""
        set(value) = pref.edit { putString(USER_NAME, value) }

    fun clear() {
        pref.edit {
            remove(ACCESS_TOKEN)
            remove(USER_NAME)
        }
    }

    companion object {
        const val FILE_NAME = "AuthSharedPreferences"
        const val USER_NAME = "UserName"
        const val ACCESS_TOKEN = "AccessToken"

        @Volatile
        private var INSTANCE: WebsosoLocalStorage? = null

        fun getInstance(context: Context): WebsosoLocalStorage {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: WebsosoLocalStorage(context).also {
                    INSTANCE = it
                }
            }
        }
    }
}