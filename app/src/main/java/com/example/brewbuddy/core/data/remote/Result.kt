package com.example.brewbuddy.core.data.remote

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val exception: ApiException) : Result<Nothing>()
}