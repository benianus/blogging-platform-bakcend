package com.jetbrains.bloggingplatformbackend.mapper.usermapper

import com.jetbrains.bloggingplatformbackend.dto.auth.RegisterResponseDto
import com.jetbrains.bloggingplatformbackend.dto.user.UserDto
import com.jetbrains.bloggingplatformbackend.dto.user.UserUpdateDto
import com.jetbrains.bloggingplatformbackend.entity.User

fun UserUpdateDto.toEntity() =
    User().apply {
        this.username = this@toEntity.username
        this.password = this@toEntity.password
    }

fun User.toDto() =
    UserDto(
        id = id,
        username = username,
        createdAt = createdAt,
        updatedAt = updatedAt,
        articles = articles
    )

fun User.toRegisterResponseDto() =
    RegisterResponseDto(
        id = id,
        username = username,
        role = role,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
