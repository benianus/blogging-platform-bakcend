package com.jetbrains.bloggingplatformbackend.dto.auth

import java.util.UUID

data class RegisterResponseDto(
    val id: UUID?,
    val username: String,
    val role: String,
    val createdAt: String,
    val updatedAt: String
)