package com.serdar.stockfeed.data

data class NewsData(
    val status: String?,
    val totalResults: Int,
    val articles: List<Article>?,
)