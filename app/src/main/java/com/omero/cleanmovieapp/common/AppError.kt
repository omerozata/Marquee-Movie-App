package com.omero.cleanmovieapp.common

import kotlinx.serialization.SerializationException

import retrofit2.HttpException
import java.io.IOException

sealed interface AppError {

    data object NoConnection : AppError
    data class Http(val code: Int) : AppError
    data object Serialization: AppError
    data class Api(val message: String): AppError
    data object Unknown: AppError
}

class ApiException(val apiMessage: String) : Exception(apiMessage)

fun Throwable.toAppError(): AppError = when (this){
    is ApiException -> AppError.Api(apiMessage)
    is HttpException -> AppError.Http(code())
    is SerializationException -> AppError.Serialization
    is IOException -> AppError.NoConnection
    else -> AppError.Unknown
}