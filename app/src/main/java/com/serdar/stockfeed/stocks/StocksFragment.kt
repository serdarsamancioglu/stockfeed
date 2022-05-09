package com.serdar.stockfeed.stocks

import androidx.recyclerview.widget.LinearLayoutManager
import com.serdar.stockfeed.BaseFragment
import com.serdar.stockfeed.R
import com.serdar.stockfeed.adapter.StocksAdapter
import com.serdar.stockfeed.databinding.FragmentStocksBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream

class StocksFragment: BaseFragment<FragmentStocksBinding>(R.layout.fragment_stocks) {

    private val viewModel: StocksViewModel by viewModel()

    override fun init() {

        viewModel.bytes.observe(this) {
            writeFile(it)
        }
        viewModel.stocks.observe(this) {
            val adapter = StocksAdapter(it, this)
            binding.rvStocks.adapter = adapter
            binding.rvStocks.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun writeFile(bytes: ByteArray) {
        val fileDir = context?.cacheDir
        val file = File.createTempFile("stocks", ".csv", fileDir)
        val writer = FileOutputStream(file)
        writer.write(bytes)
        writer.close()
        viewModel.parseFile(file.inputStream())
    }
}