package com.jetbrains.bloggingplatformbackend.exceptionHandler


data class GlobalResponse<T>(
    val data: T?,
    val status: String = SUCCESS,
    val errors: List<ErrorItem>? = null
) {
    constructor(errors: List<ErrorItem>) : this(
        data = null,
        status = ERROR,
        errors = errors
    )

    data class ErrorItem(val message: String?)
    
    companion object {
        const val ERROR = "error"
        const val SUCCESS = "success"
    }
}
