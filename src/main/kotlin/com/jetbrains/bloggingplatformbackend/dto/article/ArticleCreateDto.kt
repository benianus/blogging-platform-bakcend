package com.jetbrains.bloggingplatformbackend.dto.article

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class ArticleCreateDto(
    @NotBlank
    @NotNull
    val title: String,

    @NotNull
    @NotBlank
    val content: String,
)