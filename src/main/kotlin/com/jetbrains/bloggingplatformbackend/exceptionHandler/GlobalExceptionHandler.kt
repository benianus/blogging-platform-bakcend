package com.jetbrains.bloggingplatformbackend.exceptionHandler

import com.jetbrains.bloggingplatformbackend.exceptionHandler.GlobalResponse.ErrorItem
import jakarta.validation.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<GlobalResponse<*>> =
        ResponseEntity(GlobalResponse<ErrorItem>(listOf(ErrorItem(e.message))), HttpStatus.INTERNAL_SERVER_ERROR)

    @ExceptionHandler(CustomExceptionHandler::class)
    fun handleCustomException(e: CustomExceptionHandler): ResponseEntity<*> =
        ResponseEntity(
            GlobalResponse<ErrorItem>(errors = listOf(ErrorItem(e.message))),
            HttpStatus.resolve(e.code) as HttpStatusCode
        )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(e: MethodArgumentNotValidException): ResponseEntity<*> {
        val errors = e.bindingResult.fieldErrors.map { ErrorItem(it.defaultMessage) }
        return ResponseEntity(GlobalResponse<ErrorItem>(errors = errors), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(e: ValidationException): ResponseEntity<*> =
        ResponseEntity(
            GlobalResponse<ErrorItem>(listOf(ErrorItem(e.message))),
            HttpStatus.BAD_REQUEST
        )

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(e: AuthenticationException): ResponseEntity<*> =
        ResponseEntity(
            GlobalResponse<ErrorItem>(errors = listOf(ErrorItem(e.message))),
            HttpStatus.UNAUTHORIZED
        )

}