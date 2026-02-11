package com.jetbrains.bloggingplatformbackend.mapper.authmapper

import com.jetbrains.bloggingplatformbackend.dto.auth.LoginRequestDto
import com.jetbrains.bloggingplatformbackend.dto.auth.RegisterRequestDto
import com.jetbrains.bloggingplatformbackend.dto.auth.RegisterResponseDto
import com.jetbrains.bloggingplatformbackend.entity.User

fun RegisterRequestDto.toEntity() =
    User().apply {
        this.username = this@toEntity.username
        this.password = this@toEntity.password
    }

fun LoginRequestDto.toEntity() =
    User().apply {
        this.username = this@toEntity.username
        this.password = this@toEntity.password
    }

