package com.jetbrains.bloggingplatformbackend.controller

import com.jetbrains.bloggingplatformbackend.abstracts.ArticleService
import com.jetbrains.bloggingplatformbackend.abstracts.UserService
import com.jetbrains.bloggingplatformbackend.dto.article.ArticleCreateDto
import com.jetbrains.bloggingplatformbackend.dto.article.ArticleDto
import com.jetbrains.bloggingplatformbackend.dto.article.ArticleIsFavoriteUpdateDto
import com.jetbrains.bloggingplatformbackend.dto.article.ArticleUpdateDto
import com.jetbrains.bloggingplatformbackend.exceptionHandler.GlobalResponse
import com.jetbrains.bloggingplatformbackend.mapper.articlemapper.toDto
import com.jetbrains.bloggingplatformbackend.mapper.articlemapper.toEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/articles")
class ArticleController(
    private val articleService: ArticleService,
    private val userService: UserService
) {
    @GetMapping
    fun findAll(): ResponseEntity<*> {
        val articles = articleService.findAll().map { it.toDto() }
        return ResponseEntity(
            GlobalResponse(articles),
            HttpStatus.OK
        )
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<GlobalResponse<ArticleDto>> {
        return ResponseEntity(
            GlobalResponse(articleService.findById(id).toDto()),
            HttpStatus.OK
        )
    }

    @PostMapping
    fun save(@RequestBody articleCreateDto: ArticleCreateDto): ResponseEntity<GlobalResponse<ArticleDto>> {
        val loggedUser = SecurityContextHolder.getContext()?.authentication?.principal as UserDetails
        val foundedUser = userService.findByUsername(loggedUser.username)

        val article = articleService.save(articleCreateDto.toEntity().apply { user = foundedUser })

        return ResponseEntity(
            GlobalResponse(article.toDto()),
            HttpStatus.CREATED
        )
    }

    @PutMapping("/{id}")
    fun update(
        @RequestBody articleUpdateDto: ArticleUpdateDto,
        @PathVariable id: UUID
    ): ResponseEntity<GlobalResponse<ArticleDto>> {
        val article = articleService.update(id, articleUpdateDto.toEntity())
        return ResponseEntity(
            GlobalResponse(article.toDto()),
            HttpStatus.OK
        )
    }

    @PutMapping("/{id}/is-favorite")
    fun updateIsFavorite(
        @RequestBody articleIsFavoriteUpdateDto: ArticleIsFavoriteUpdateDto,
        @PathVariable id: UUID
    ): ResponseEntity<GlobalResponse<ArticleDto>> {
        val article = articleService.updateIsFavorite(id, articleIsFavoriteUpdateDto.toEntity())
        return ResponseEntity(
            GlobalResponse(article.toDto()),
            HttpStatus.OK
        )
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        articleService.delete(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}