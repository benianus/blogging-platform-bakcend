package com.jetbrains.bloggingplatformbackend.controller

import com.jetbrains.bloggingplatformbackend.abstracts.AuthService
import com.jetbrains.bloggingplatformbackend.dto.auth.LoginRequestDto
import com.jetbrains.bloggingplatformbackend.dto.auth.LoginResponseDto
import com.jetbrains.bloggingplatformbackend.dto.auth.RefreshTokenRequestDto
import com.jetbrains.bloggingplatformbackend.dto.auth.RefreshTokenResponseDto
import com.jetbrains.bloggingplatformbackend.dto.auth.RegisterRequestDto
import com.jetbrains.bloggingplatformbackend.dto.auth.RegisterResponseDto
import com.jetbrains.bloggingplatformbackend.exceptionHandler.GlobalResponse
import com.jetbrains.bloggingplatformbackend.mapper.authmapper.toEntity
import com.jetbrains.bloggingplatformbackend.mapper.usermapper.toRegisterResponseDto
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/login")
    fun login(
        @RequestBody @Valid loginRequestDto: LoginRequestDto
    ): ResponseEntity<GlobalResponse<LoginResponseDto>> =
        ResponseEntity.ok(GlobalResponse(authService.login(loginRequestDto)))

    @PostMapping("/register")
    fun register(
        @RequestBody @Valid registerRequestDto: RegisterRequestDto
    ): ResponseEntity<GlobalResponse<RegisterResponseDto>> {
        val user = authService.register(registerRequestDto.toEntity()).toRegisterResponseDto()
        return ResponseEntity(
            GlobalResponse(user),
            HttpStatus.CREATED
        )
    }

    @PostMapping("/refresh")
    fun refreshToken(
        @RequestBody refreshTokenRequest: RefreshTokenRequestDto
    ): ResponseEntity<GlobalResponse<RefreshTokenResponseDto>> =
        ResponseEntity.ok(
            GlobalResponse(
                authService.refreshToken(refreshTokenRequest)
            )
        )
}