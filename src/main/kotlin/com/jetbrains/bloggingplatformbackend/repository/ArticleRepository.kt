package com.jetbrains.bloggingplatformbackend.repository

import com.jetbrains.bloggingplatformbackend.entity.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

interface ArticleRepository : JpaRepository<Article, UUID> {
    @Query("select * from articles where user_id = :userId", nativeQuery = true)
    fun findAllById(@Param("userId") userId: UUID?): List<Article>
}