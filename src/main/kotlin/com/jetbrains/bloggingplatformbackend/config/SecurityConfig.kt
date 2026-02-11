package com.jetbrains.bloggingplatformbackend.config

import com.jetbrains.bloggingplatformbackend.entity.JwtProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val jwtFilter: JwtFilter,
) {
    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain =
        httpSecurity
            .cors { it.disable() }
            .csrf { it.disable() }
            .authorizeHttpRequests {
//                it.requestMatchers("/api/v1/auth/**").permitAll()
//                    .requestMatchers(
//                        "/api/v1/users/**",
//                        "/api/v1/articles/**"
//                    ).hasAnyRole("USER", "ADMIN")
//                    .anyRequest().authenticated()
                it.anyRequest().permitAll()
            }
//            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
//            .authenticationManager(authenticationManager(httpSecurity))
            .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(httpSecurity: HttpSecurity): AuthenticationManager {
        val authBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder::class.java)
        authBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
        return authBuilder.build()
    }

}