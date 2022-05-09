package com.serdar.stockfeed.koin

import com.serdar.stockfeed.repository.NewsRepository
import com.serdar.stockfeed.repository.StockRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { NewsRepository(get()) }
    factory { StockRepository(get()) }
}