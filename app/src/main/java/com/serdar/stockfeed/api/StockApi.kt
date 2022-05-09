package com.serdar.stockfeed.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface StockApi {
    @GET("https://raw.githubusercontent.com/dsancov/TestData/main/stocks.csv")
    fun fetchStockPrices(): Call<ResponseBody>
}