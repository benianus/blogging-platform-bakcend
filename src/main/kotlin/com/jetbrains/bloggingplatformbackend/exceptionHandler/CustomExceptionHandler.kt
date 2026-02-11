package com.jetbrains.bloggingplatformbackend.exceptionHandler

class CustomExceptionHandler(
    override val message: String?,
    val code: Int
) : RuntimeException() {
    companion object {
        fun resourceNotFound(message: String): CustomExceptionHandler =
            CustomExceptionHandler(message, 400)

        fun invalidCredentials(message: String): CustomExceptionHandler =
            CustomExceptionHandler(message, 400)

        fun invalidToken(message: String): CustomExceptionHandler =
            CustomExceptionHandler(message, 403)
    }
}