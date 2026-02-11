package com.jetbrains.bloggingplatformbackend.util

import com.jetbrains.bloggingplatformbackend.exceptionHandler.CustomExceptionHandler
import com.jetbrains.bloggingplatformbackend.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class SecurityUtils(
    private val userRepository: UserRepository
) {
    fun isOwner(articleId: UUID): Boolean {
        val username = (SecurityContextHolder.getContext().authentication?.principal as UserDetails).username
        val user = userRepository.findOneByUsername(username)
            ?: throw CustomExceptionHandler.resourceNotFound("user not found")
        return userRepository.isOwner(articleId, user.id)
    }
}