package com.teamwss.websoso.data.authInterceptor

import com.teamwss.websoso.App
import com.teamwss.websoso.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    private val encodedToken: String
        get() = "Bearer ${BuildConfig.TEST_TOKEN}"

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val headerRequest = originalRequest.newBuilder()
            .header("Authorization", encodedToken)
            .build()
        return chain.proceed(headerRequest)
    }

}