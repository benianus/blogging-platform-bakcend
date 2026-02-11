package com.jetbrains.bloggingplatformbackend.repository

import com.jetbrains.bloggingplatformbackend.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
    fun findOneByUsername(username: String): User?

    @Query("select count(a) > 0 from Article a where a.id = :id and a.user.id = :userId")
    fun isOwner(@Param("id") id: UUID, @Param("userId") userId: UUID?): Boolean

}