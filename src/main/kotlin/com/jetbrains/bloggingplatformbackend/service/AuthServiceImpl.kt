package com.jetbrains.bloggingplatformbackend.service

import com.jetbrains.bloggingplatformbackend.abstracts.AuthService
import com.jetbrains.bloggingplatformbackend.config.JwtHelper
import com.jetbrains.bloggingplatformbackend.dto.auth.LoginRequestDto
import com.jetbrains.bloggingplatformbackend.dto.auth.LoginResponseDto
import com.jetbrains.bloggingplatformbackend.dto.auth.RefreshTokenRequestDto
import com.jetbrains.bloggingplatformbackend.dto.auth.RefreshTokenResponseDto
import com.jetbrains.bloggingplatformbackend.entity.User
import com.jetbrains.bloggingplatformbackend.exceptionHandler.CustomExceptionHandler
import com.jetbrains.bloggingplatformbackend.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val userDetailsService: UserDetailsService,
    private val authenticationManager: AuthenticationManager,
    private val jwtHelper: JwtHelper,
    private val passwordEncoder: PasswordEncoder,
) : AuthService {

    @Transactional
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
            userId = foundedUser?.id,
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

    @Transactional
    override fun refreshToken(refreshTokenRequest: RefreshTokenRequestDto): RefreshTokenResponseDto {
        val username = jwtHelper.extractClaim(refreshTokenRequest.refreshToken) { it.subject }
        val userDetails = userDetailsService.loadUserByUsername(username)
        val foundedUser = userRepository.findOneByUsername(username)
            ?: throw CustomExceptionHandler.resourceNotFound("user not found")
        val isRefreshTokenValid = jwtHelper.isTokenValid(refreshTokenRequest.refreshToken, userDetails)

        val claims = mapOf(
            "userId" to foundedUser.id,
            "role" to foundedUser.role,
        )

        val accessToken = jwtHelper.generateAccessToken(claims, userDetails)
        val refreshToken = jwtHelper.generateRefreshToken(claims, userDetails)

        if (!isRefreshTokenValid) {
            throw CustomExceptionHandler.invalidToken("Invalid refresh token")
        }

        return RefreshTokenResponseDto(accessToken, refreshToken)
    }
}