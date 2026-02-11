package com.jetbrains.bloggingplatformbackend.entity

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import kotlin.time.Clock
import kotlin.time.Instant

@Entity
@Table(name = "articles")
class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID?,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", columnDefinition = "text", nullable = false)
    var content: String,

    @Column(name = "is_favorite", columnDefinition = "boolean default false")
    var isFavorite: Boolean,

    @CreationTimestamp
    @Column(name = "published_at")
    var publishedAt: String,

    @UpdateTimestamp
    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id", nullable = true)
    @JsonProperty(value = "userId")
    var user: User
) {
    constructor() : this(
        null,
        "",
        "",
        false,
        Clock.System.now().toString(),
        Clock.System.now().toString(),
        User()
    )

    fun getUser(): UUID = user.id ?: UUID.randomUUID()
}