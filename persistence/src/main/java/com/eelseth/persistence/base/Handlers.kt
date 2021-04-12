package com.eelseth.persistence.base

import timber.log.Timber

internal suspend fun databaseOperation(operation: suspend () -> Unit): DBOperationResult {
    return try {
        operation.invoke()
        DBOperationResult.Success
    } catch (throwable: Throwable) {
        Timber.e(throwable, "Database Operation failed")
        DBOperationResult.Error(throwable)
    }
}
