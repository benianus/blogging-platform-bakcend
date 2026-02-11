package com.jetbrains.bloggingplatformbackend.service

import com.jetbrains.bloggingplatformbackend.abstracts.ArticleService
import com.jetbrains.bloggingplatformbackend.entity.Article
import com.jetbrains.bloggingplatformbackend.exceptionHandler.CustomExceptionHandler
import com.jetbrains.bloggingplatformbackend.repository.ArticleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ArticleServiceImpl(
    private val articleRepository: ArticleRepository,
) : ArticleService {
    override fun findAll(): List<Article> = articleRepository.findAll()

    override fun findById(id: UUID): Article {
        return articleRepository.findByIdOrNull(id)
            ?: throw CustomExceptionHandler.resourceNotFound("article not found")
    }

    @Transactional
    override fun save(article: Article): Article = articleRepository.save(article)

    @Transactional
    @PreAuthorize("@securityUtils.isOwner(#id)")
    override fun update(id: UUID, article: Article): Article {
        val updatedArticle = articleRepository.findByIdOrNull(id)?.apply {
            title = article.title
            content = article.content
        } ?: throw CustomExceptionHandler.resourceNotFound("article not found")

        return articleRepository.save(updatedArticle)
    }

    @Transactional
    @PreAuthorize("@securityUtils.isOwner(#id)")
    override fun updateIsFavorite(id: UUID, article: Article): Article {
        val updatedArticle = articleRepository.findByIdOrNull(id)?.apply {
            isFavorite = article.isFavorite
        } ?: throw CustomExceptionHandler.resourceNotFound("article not found")

        return articleRepository.save(updatedArticle)
    }

    @PreAuthorize("@securityUtils.isOwner(#id)")
    override fun delete(id: UUID) = articleRepository.findByIdOrNull(id)?.let {
        articleRepository.delete(it)
    } ?: throw CustomExceptionHandler.resourceNotFound("article not found")

}