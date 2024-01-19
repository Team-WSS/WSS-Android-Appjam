package com.teamwss.websoso.data.repository

import com.teamwss.websoso.data.mapper.UserInfoMyPageResponseMapper.toData
import com.teamwss.websoso.data.model.UserInfoMyPageEntity
import com.teamwss.websoso.data.remote.response.LoginResponse
import com.teamwss.websoso.data.remote.service.AuthService

class AuthRepository(
    private val authService: AuthService
) {

    suspend fun postLogin(userId: Long): LoginResponse {
        return authService.postLogin(userId)
    }

    suspend fun getMyPageUserInfo(): UserInfoMyPageEntity {
        return authService.getMyPageUserInfo().toData()
    }
}