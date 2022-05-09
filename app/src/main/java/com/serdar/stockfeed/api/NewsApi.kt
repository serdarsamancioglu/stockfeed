package com.serdar.stockfeed.api

import com.serdar.stockfeed.data.NewsData
import retrofit2.Call
import retrofit2.http.GET

interface NewsApi {
    @GET("everything/cnn.json")
    fun fetchCnnNews(): Call<NewsData>
}