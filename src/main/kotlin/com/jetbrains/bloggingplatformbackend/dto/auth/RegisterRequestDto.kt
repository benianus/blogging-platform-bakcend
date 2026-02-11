package com.jetbrains.bloggingplatformbackend.dto.auth

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class RegisterRequestDto(
    @NotBlank(message = "username is required")
    @NotNull(message = "username is required")
    @Size(min = 3, message = "min characters is 3")
    val username: String,

    @NotBlank(message = "password is required")
    @NotNull(message = "password is required")
    @Pattern(regexp = "^[a-zA-Z0-9_]{3,16}$")
    @Size(min = 6, message = "min characters is 6")
    val password: String
)