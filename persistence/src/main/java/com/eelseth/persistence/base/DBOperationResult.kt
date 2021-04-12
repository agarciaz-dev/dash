package com.eelseth.persistence.base

sealed class DBOperationResult {
    object Success : DBOperationResult()
    data class Error(val throwable: Throwable) : DBOperationResult()
}