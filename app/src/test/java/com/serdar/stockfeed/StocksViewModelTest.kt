package com.serdar.stockfeed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.serdar.stockfeed.repository.StockRepository
import com.serdar.stockfeed.stocks.StocksViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.ByteArrayInputStream
import java.util.*


class StocksViewModelTest {

    private val NEW_LINE = "\n"

    @MockK
    lateinit var stocksRepository: StockRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: StocksViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)

        val byteArray = byteArrayOf(10, 24, 82, 90 ,100)
        every { runBlocking { stocksRepository.fetchStocks() } } returns byteArray
        viewModel = StocksViewModel(stocksRepository)
    }

    @Test
    fun testStocks() {
        val byteArray = byteArrayOf(10, 24, 82, 90 ,100)
        every { runBlocking { stocksRepository.fetchStocks() } } returns byteArray
        assertTrue(Arrays.equals(byteArray, viewModel.bytes.value))
    }

    @Test
    fun testParseFile() {
        val inputStream = ByteArrayInputStream(getTestFile().toByteArray())
        viewModel.parseFile(inputStream)
        //Comparing actual stock list with the mock list which has pre-formatted prices
        val mockList = getMockStockList()
        val actualList = viewModel.stocks.value
        for (i in 0 until (mockList.size)) {
            assertEquals(mockList[i].first, actualList?.get(i)?.first)
            assertEquals(mockList[i].second.value, actualList?.get(i)?.second?.value)
        }
    }

    private fun getTestFile(): String {
        val file = StringBuilder()
        for (s in getStockList()) {
            file.append(NEW_LINE)
            file.append(s)
        }
        file.removePrefix(NEW_LINE)
        return file.toString()
    }

    private fun getStockList(): List<String> {
        val list = mutableListOf<String>()
        list.add("AAPL,1430")
        list.add("MSFT,246")
        list.add("GOOG,2188")
        list.add("AMZN,1070")
        return list
    }

    private fun getMockStockList(): List<Pair<String, MutableLiveData<String>>> {
        val list = mutableListOf<Pair<String, MutableLiveData<String>>>()
        list.add(Pair("AAPL", MutableLiveData("1,430.00")))
        list.add(Pair("MSFT", MutableLiveData("246.00")))
        list.add(Pair("GOOG", MutableLiveData("2,188.00")))
        list.add(Pair("AMZN", MutableLiveData("1,070.00")))
        return list
    }
}