package com.serdar.stockfeed.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serdar.stockfeed.api.ApiResult
import com.serdar.stockfeed.data.Article
import com.serdar.stockfeed.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(private val newsRepository: NewsRepository): ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>>
        get() = _articles

    init {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                when (val result = newsRepository.fetchNews()) {
                    is ApiResult.Success -> {
                        result.response.articles?.let {
                            _articles.postValue(it)
                        }
                    }
                }
            }
        }
    }
}