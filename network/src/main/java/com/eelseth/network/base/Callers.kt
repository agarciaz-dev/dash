package com.eelseth.network.base

import retrofit2.Response

internal suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): ApiResult<T> {
    val response: Response<T>
    return try {
        response = call.invoke()
        if (response.isSuccessful) {
            val body = response.body()
            if (body == null) {
                ApiResult.Error(NullPointerException())
            } else {
                ApiResult.Success(body)
            }
        } else {
            ApiResult.Error(Throwable("Default api error"))
        }
    } catch (throwable: Throwable) {
        return ApiResult.Error(throwable)
    }
}