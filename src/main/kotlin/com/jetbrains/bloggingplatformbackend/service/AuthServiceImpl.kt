package com.jetbrains.bloggingplatformbackend.service

import com.jetbrains.bloggingplatformbackend.abstracts.AuthService
import com.jetbrains.bloggingplatformbackend.config.JwtHelper
import com.jetbrains.bloggingplatformbackend.dto.auth.LoginRequestDto
import com.jetbrains.bloggingplatformbackend.dto.auth.LoginResponseDto
import com.jetbrains.bloggingplatformbackend.dto.auth.RefreshTokenRequest
import com.jetbrains.bloggingplatformbackend.dto.auth.RefreshTokenResponse
import com.jetbrains.bloggingplatformbackend.entity.User
import com.jetbrains.bloggingplatformbackend.exceptionHandler.CustomExceptionHandler
import com.jetbrains.bloggingplatformbackend.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager,
    private val jwtHelper: JwtHelper,
    private val passwordEncoder: PasswordEncoder,
) : AuthService {
    override fun login(loginRequestDto: LoginRequestDto): LoginResponseDto {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequestDto.username,
                loginRequestDto.password
            )
        )

        val userDetails = authentication.principal as UserDetails
        val foundedUser = userRepository.findOneByUsername(loginRequestDto.username)
        val claims = mapOf(
            "userId" to foundedUser?.id,
            "role" to foundedUser?.role,
        )

        val accessToken = jwtHelper.generateAccessToken(claims, userDetails)
        val refreshToken = jwtHelper.generateRefreshToken(claims, userDetails)

        return LoginResponseDto(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    override fun register(user: User): User {
        user.apply { password = passwordEncoder.encode(user.password)!! }
        return userRepository.save(user)
    }

    override fun logout() {
        TODO("Not yet implemented")
    }

    override fun refreshToken(refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse {
        TODO("Not yet implemented")
    }
}