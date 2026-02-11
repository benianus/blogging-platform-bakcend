package com.jetbrains.bloggingplatformbackend.dto.auth

data class LoginRequestDto(
    val username: String,
    val password: String
)