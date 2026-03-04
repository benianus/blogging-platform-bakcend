package com.jetbrains.bloggingplatformbackend.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val user: User,
) : UserDetails {
    override fun getAuthorities(): Collection<out GrantedAuthority> = listOf(SimpleGrantedAuthority(user.role))

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.username
}