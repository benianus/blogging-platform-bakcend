package com.jetbrains.bloggingplatformbackend.service

import com.jetbrains.bloggingplatformbackend.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findOneByUsername(username) ?: throw UsernameNotFoundException("user not found")

        return User
            .withUsername(user.username)
            .username(user.username)
            .password(user.password)
            .roles(user.role)
            .build()
    }
}