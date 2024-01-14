package com.teamwss.websoso.data

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.teamwss.websoso.BuildConfig
import com.teamwss.websoso.data.remote.service.AuthService
import com.teamwss.websoso.data.remote.service.AvatarService
import com.teamwss.websoso.data.remote.service.MemoService
import com.teamwss.websoso.data.remote.service.NovelService
import com.teamwss.websoso.data.remote.service.UserNovelService
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object NetworkModule {
    private const val BASE_URL = BuildConfig.BASE_URL
    private const val CONTENT_TYPE = "application/json"
    private val json: Json = Json {
        ignoreUnknownKeys = true
    }

    private fun getLogOkHttpClient(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("Retrofit2", "CONNECTION INFO -> $message")
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(getLogOkHttpClient())
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory(CONTENT_TYPE.toMediaType()))
        .build()

    inline fun <reified T> create(): T = retrofit.create<T>(T::class.java)
}

object ServicePool {
    val authService: AuthService by lazy { NetworkModule.create() }
    val userNovelService: UserNovelService by lazy { NetworkModule.create() }
    val novelService: NovelService by lazy { NetworkModule.create() }
    val avatarService: AvatarService by lazy { NetworkModule.create() }
    val memoService: MemoService by lazy { NetworkModule.create() }
}
