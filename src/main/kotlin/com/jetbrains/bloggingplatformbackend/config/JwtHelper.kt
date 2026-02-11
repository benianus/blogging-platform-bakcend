package com.jetbrains.bloggingplatformbackend.config

import com.jetbrains.bloggingplatformbackend.entity.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtHelper(
    jwtProperties: JwtProperties,
) {
    val key: SecretKey = Keys.hmacShaKeyFor(jwtProperties.key.toByteArray())
    val accessToken: Long = jwtProperties.accessTokenExpiration
    val refreshToken: Long = jwtProperties.refreshTokenExpiration

    fun generateAccessToken(claims: Map<String, Any?>, userDetails: UserDetails): String =
        generateToken(claims, accessToken, userDetails)

    fun generateRefreshToken(claims: Map<String, Any?>, userDetails: UserDetails): String =
        generateToken(claims, refreshToken, userDetails)

    fun generateToken(claims: Map<String, Any?>, expiration: Long, userDetails: UserDetails): String {
        return Jwts
            .builder()
            .claims(claims)
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(key)
            .compact()
    }

    fun extractClaims(token: String): Claims {
        return Jwts
            .parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractClaims(token)
        return claimsResolver(claims)
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractClaim(token) { it.subject }
        val expirationDate = extractClaim(token) { it.expiration }

        val userMatch = userDetails.username == username
        val isTokenExpired = expirationDate.before(Date(System.currentTimeMillis()))

        return userMatch && !isTokenExpired
    }
}