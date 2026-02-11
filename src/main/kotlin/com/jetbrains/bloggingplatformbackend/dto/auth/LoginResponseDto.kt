package com.jetbrains.bloggingplatformbackend.dto.auth

data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
)
