package com.jetbrains.bloggingplatformbackend.abstracts

import com.jetbrains.bloggingplatformbackend.entity.User
import java.util.UUID

interface UserService {
    fun findAll(): List<User>
    fun findById(id: UUID): User
    fun findByUsername(username: String): User
    fun update(user: User, id: UUID): User
    fun delete(id: UUID)
}
