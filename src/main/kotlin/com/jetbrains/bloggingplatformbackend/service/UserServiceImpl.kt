package com.jetbrains.bloggingplatformbackend.service

import com.jetbrains.bloggingplatformbackend.abstracts.UserService
import com.jetbrains.bloggingplatformbackend.entity.User
import com.jetbrains.bloggingplatformbackend.exceptionHandler.CustomExceptionHandler
import com.jetbrains.bloggingplatformbackend.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {
    override fun findAll(): List<User> = userRepository.findAll()

    override fun findById(id: UUID): User =
        userRepository.findByIdOrNull(id) ?: throw CustomExceptionHandler.resourceNotFound("user not found")

    override fun findByUsername(username: String): User =
        userRepository.findOneByUsername(username) ?: throw CustomExceptionHandler.resourceNotFound("user not found")

    override fun update(user: User, id: UUID): User {
        val foundedUser = userRepository.findByIdOrNull(id)?.apply {
            username = user.username
            password = passwordEncoder.encode(user.password).toString()
        } ?: throw CustomExceptionHandler.resourceNotFound("user not found")

        return userRepository.save(foundedUser)
    }

    override fun delete(id: UUID) {
        val foundedUser =
            userRepository.findByIdOrNull(id) ?: throw CustomExceptionHandler.resourceNotFound("user not found")

        userRepository.delete(foundedUser)
    }
}