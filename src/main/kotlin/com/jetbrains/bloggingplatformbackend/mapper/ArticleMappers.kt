package com.jetbrains.bloggingplatformbackend.mapper.articlemapper

import com.jetbrains.bloggingplatformbackend.dto.article.ArticleCreateDto
import com.jetbrains.bloggingplatformbackend.dto.article.ArticleDto
import com.jetbrains.bloggingplatformbackend.dto.article.ArticleIsFavoriteUpdateDto
import com.jetbrains.bloggingplatformbackend.dto.article.ArticleUpdateDto
import com.jetbrains.bloggingplatformbackend.entity.Article

fun Article.toDto(): ArticleDto {
    return ArticleDto(
        id = id,
        title = title,
        content = content,
        isFavorite = isFavorite,
        publishedAt = publishedAt,
        updatedAt = updatedAt,
        userId = user.id
    )
}

fun ArticleCreateDto.toEntity(): Article = Article().apply {
    this.title = this@toEntity.title
    this.content = this@toEntity.content
}

fun ArticleUpdateDto.toEntity(): Article = Article().apply {
    this.title = this@toEntity.title
    this.content = this@toEntity.content
}

fun ArticleIsFavoriteUpdateDto.toEntity(): Article = Article().apply {
    this.isFavorite = this@toEntity.isFavorite
}