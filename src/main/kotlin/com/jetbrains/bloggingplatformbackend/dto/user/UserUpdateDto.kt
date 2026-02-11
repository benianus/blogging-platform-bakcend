package com.jetbrains.bloggingplatformbackend.dto.user

import java.util.UUID

data class UserUpdateDto(
    val username: String,
    val password: String,
)
