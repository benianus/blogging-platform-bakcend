package com.jetbrains.bloggingplatformbackend.abstracts

import com.jetbrains.bloggingplatformbackend.entity.Article
import java.util.*

interface ArticleService {
    fun findAll(): List<Article>
    fun findById(id: UUID): Article
    fun save(article: Article): Article
    fun update(id: UUID, article: Article): Article
    fun updateIsFavorite(id: UUID, article: Article): Article
    fun delete(id: UUID)
}