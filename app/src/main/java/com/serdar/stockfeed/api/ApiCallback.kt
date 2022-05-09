package com.serdar.stockfeed.api

interface ApiCallback<T> {
    fun onSuccess(response: T)
    fun onError(message: String)
}