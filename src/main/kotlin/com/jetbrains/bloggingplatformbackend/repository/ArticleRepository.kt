package com.jetbrains.bloggingplatformbackend.repository

import com.jetbrains.bloggingplatformbackend.entity.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

interface ArticleRepository : JpaRepository<Article, UUID>