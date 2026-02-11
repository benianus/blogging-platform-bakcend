package com.jetbrains.bloggingplatformbackend.dto.article

import java.util.*

data class ArticleDto(
    val id: UUID?,
    val title: String,
    val content: String,
    val isFavorite: Boolean,
    val publishedAt: String,
    val updatedAt: String,
    val userId: UUID?
)