package com.serdar.stockfeed.api

sealed class ApiResult<out R> {
    data class Success<out R>(val response: R): ApiResult<R>()
    data class Failure(val errorMessage: String): ApiResult<Nothing>()
}