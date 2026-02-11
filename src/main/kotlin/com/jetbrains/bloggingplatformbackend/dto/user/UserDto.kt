package com.jetbrains.bloggingplatformbackend.dto.user

import com.jetbrains.bloggingplatformbackend.entity.Article
import java.util.*

data class UserDto(
    val id: UUID?,
    val username: String,
    val createdAt: String,
    val updatedAt: String,
    val articles: List<Article>?
)