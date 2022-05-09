package com.serdar.stockfeed.koin

import com.serdar.stockfeed.news.NewsViewModel
import com.serdar.stockfeed.stocks.StocksViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { StocksViewModel(get()) }
    viewModel { NewsViewModel(get()) }
}