package com.serdar.stockfeed.stocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serdar.stockfeed.*
import com.serdar.stockfeed.data.Stock
import com.serdar.stockfeed.repository.StockRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class StocksViewModel(private val stockRepository: StockRepository): ViewModel() {

    private val _stocks = MutableLiveData<List<Pair<String, MutableLiveData<String>>>>()
    val stocks: LiveData<List<Pair<String, MutableLiveData<String>>>>
        get() = _stocks

    private val prices = mutableMapOf<String, MutableList<String>>()

    private val _bytes = MutableLiveData<ByteArray>()
    val bytes: LiveData<ByteArray>
        get() = _bytes

    init {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val fileBytes = stockRepository.fetchStocks()
                fileBytes?.let {
                    _bytes.postValue(it)
                }
            }
        }
    }

    fun parseFile(inputStream: InputStream) {
        val list = mutableListOf<Pair<String, MutableLiveData<String>>>()
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.readLine()
        var line = reader.readLine()
        while (line != null) {
            val row = line.split(",")
            val name = row[0].trim().replace(QUOTATION, EMPTY)
            val price = getFormattedAmount(row[1].trim().replace(QUOTATION, EMPTY).toDouble())
            if (!list.map { item -> item.first }.contains(name)) {
                list.add(Pair(name, MutableLiveData(price)))
            }
            addPrice(Stock(name, price))
            line = reader.readLine()
        }
        _stocks.postValue(list)
        getRandomPrices()
    }

    private fun addPrice(stock: Stock) {
        if (prices[stock.name] == null) {
            prices[stock.name] = mutableListOf(stock.price)
        } else {
            val list = prices[stock.name]
            list?.add(stock.price)
        }
    }

    private fun getRandomPrices() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _stocks.value?.let { list ->
                    for (item in list) {
                        val price = prices[item.first]?.random()
                        item.second.postValue(price)
                    }
                }
                delay(1000)
                getRandomPrices()
            }
        }
    }

    private fun getFormattedAmount(value: Double): String {
        return try {
            val decimalFormatSymbols = DecimalFormatSymbols()
            decimalFormatSymbols.decimalSeparator = DOT[0]
            decimalFormatSymbols.groupingSeparator = COMMA[0]
            val df = DecimalFormat(AMOUNT_FORMAT, decimalFormatSymbols)
            df.minimumIntegerDigits = 1
            df.format(value)
        } catch (e: Exception) {
            e.printStackTrace()
            EMPTY
        }
    }
}