package com.jetbrains.bloggingplatformbackend.abstracts

import com.jetbrains.bloggingplatformbackend.dto.auth.*
import com.jetbrains.bloggingplatformbackend.entity.User

interface AuthService {
    fun login(loginRequestDto: LoginRequestDto): LoginResponseDto
    fun register(user: User): User
    fun logout()
    fun refreshToken(refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse
}