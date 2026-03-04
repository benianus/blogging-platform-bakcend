package com.jetbrains.bloggingplatformbackend.dto.auth

import java.util.UUID

data class LoginResponseDto(
    val userId: UUID?,
    val accessToken: String,
    val refreshToken: String,
)
