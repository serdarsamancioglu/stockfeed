package com.serdar.stockfeed.repository

import com.serdar.stockfeed.api.ApiResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.awaitResponse
import java.lang.Exception
import java.util.concurrent.TimeoutException

suspend fun <T> callApi(call: Call<T>): ApiResult<T> {
    try {
        val response = call.awaitResponse()
        if (response.isSuccessful) {
            response.body()?.let {
                return ApiResult.Success(it)
            }
        }
        return handleError(response)
    } catch (e: Exception) {
        return handleException(e)
    }
}

private fun <T> handleError(response: Response<T>): ApiResult.Failure {
    return ApiResult.Failure(
        if (!response.message().isNullOrEmpty()) {
            response.message()
        } else {
            "Something went wrong!"
        }
    )
}

fun handleException(e: Exception): ApiResult.Failure {
    return if (e is TimeoutException) {
        getTimeOutError()
    } else {
        ApiResult.Failure(e.message ?: "Something went wrong!")
    }
}

private fun getTimeOutError(): ApiResult.Failure {
    return ApiResult.Failure("Cannot connect server, please check your connection!")
}