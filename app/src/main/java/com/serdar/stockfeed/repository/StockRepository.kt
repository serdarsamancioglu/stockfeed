package com.serdar.stockfeed.repository

import com.serdar.stockfeed.api.StockApi
import retrofit2.awaitResponse

class StockRepository(private val stockService: StockApi) {

    suspend fun fetchStocks(): ByteArray? {
        try {
            val response = stockService.fetchStockPrices().awaitResponse()
            if (response.isSuccessful) {
                return response.body()?.bytes()
            }
            return null
        }
        catch (e: Exception) {
            return null
        }
    }
}