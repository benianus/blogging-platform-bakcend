@file:OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)

package com.jetbrains.bloggingplatformbackend.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID?,

    @Column(name = "username", unique = true, nullable = false)
    var username: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "role", nullable = false, columnDefinition = "varchar default 'USER'")
    var role: String,

    @CreationTimestamp
    @Column(name = "created_at")
    var createdAt: String,

    @UpdateTimestamp
    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: String,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    var articles: List<Article>
) {
    constructor() : this(
        null,
        "",
        "",
        "USER",
        Clock.System.now().toString(),
        Clock.System.now().toString(),
        emptyList(),
    )
}