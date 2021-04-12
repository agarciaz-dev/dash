package com.eelseth.core.model

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val throwable: Throwable) : Result<Nothing>()
}

fun <T : Any> Result<T>.success(): T? = (this as? Result.Success)?.data
fun <T : Any> Result<T>.error(): Throwable? = (this as? Result.Error)?.throwable