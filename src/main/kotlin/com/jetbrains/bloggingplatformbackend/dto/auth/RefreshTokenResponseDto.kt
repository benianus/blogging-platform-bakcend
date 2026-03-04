package com.jetbrains.bloggingplatformbackend.dto.auth

data class RefreshTokenResponseDto(
    val accessToken: String,
    val refreshToken: String,
)
