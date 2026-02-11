package com.jetbrains.bloggingplatformbackend.dto.auth

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String,
)
