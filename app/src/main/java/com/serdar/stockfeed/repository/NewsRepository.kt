package com.serdar.stockfeed.repository

import com.serdar.stockfeed.api.ApiResult
import com.serdar.stockfeed.api.NewsApi
import com.serdar.stockfeed.data.NewsData

class NewsRepository(private val newsService: NewsApi) {

    suspend fun fetchNews(): ApiResult<NewsData> {
        return callApi(newsService.fetchCnnNews())
    }
}