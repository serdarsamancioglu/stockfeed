package com.serdar.stockfeed.news

import android.content.Intent
import android.net.Uri
import com.serdar.stockfeed.BaseFragment
import com.serdar.stockfeed.R
import com.serdar.stockfeed.adapter.NewsAdapter
import com.serdar.stockfeed.adapter.VerticalNewsAdapter
import com.serdar.stockfeed.data.Article
import com.serdar.stockfeed.databinding.FragmentNewsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment: BaseFragment<FragmentNewsBinding>(R.layout.fragment_news) {

    private val viewModel: NewsViewModel by viewModel()

    override fun init() {
        viewModel.articles.observe(this) {
            loadNews(it)
        }
    }

    private fun loadNews(list: List<Article>) {
        loadHorizontalNews(list)
        loadVerticalNews(list)
    }

    private fun loadHorizontalNews(list: List<Article>) {
        val adapter = NewsAdapter(list.subList(0, 6)) {
            viewNews(it.url)
        }
        binding.rvNewsHorizontal.adapter = adapter
    }

    private fun loadVerticalNews(list: List<Article>) {
        val adapter = VerticalNewsAdapter(list.subList(6, list.size)) {
            viewNews(it.url)
        }
        binding.rvNewsVertical.adapter = adapter
    }

    private fun viewNews(url: String?) {
        url?.let {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}