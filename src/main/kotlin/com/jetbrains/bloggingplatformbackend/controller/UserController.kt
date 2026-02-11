package com.jetbrains.bloggingplatformbackend.controller

import com.jetbrains.bloggingplatformbackend.abstracts.UserService
import com.jetbrains.bloggingplatformbackend.dto.user.UserDto
import com.jetbrains.bloggingplatformbackend.dto.user.UserUpdateDto
import com.jetbrains.bloggingplatformbackend.exceptionHandler.GlobalResponse
import com.jetbrains.bloggingplatformbackend.mapper.usermapper.toDto
import com.jetbrains.bloggingplatformbackend.mapper.usermapper.toEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun findAll(): ResponseEntity<GlobalResponse<List<UserDto>>> = ResponseEntity.ok(
        GlobalResponse(userService.findAll().map { it.toDto() })
    )

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: UUID): ResponseEntity<GlobalResponse<UserDto>> = ResponseEntity.ok(
        GlobalResponse(userService.findById(id).toDto())
    )

    @PutMapping("/{id}")
    fun update(
        @RequestBody userUpdateDto: UserUpdateDto,
        @PathVariable id: UUID
    ): ResponseEntity<GlobalResponse<UserDto>> {
        return ResponseEntity.ok(
            GlobalResponse(userService.update(userUpdateDto.toEntity(), id).toDto())
        )
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        userService.delete(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}