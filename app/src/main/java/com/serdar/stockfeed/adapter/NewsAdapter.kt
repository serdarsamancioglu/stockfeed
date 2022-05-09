package com.serdar.stockfeed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.serdar.stockfeed.data.Article
import com.serdar.stockfeed.databinding.ItemNewsBigBinding

class NewsAdapter(private val articles: List<Article>, private val onItemClick: ((item: Article) -> Unit))
    : RecyclerView.Adapter<NewsAdapter.ArticleVH>() {

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ArticleVH, position: Int) {
        holder.bind(articles[position], onItemClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleVH {
        return ArticleVH.from(parent)
    }

    class ArticleVH(private val binding: ItemNewsBigBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article, onItemClick: ((item: Article) -> Unit)) {
            binding.tvTitle.text = article.title
            Glide.with(binding.root.context).load(article.urlToImage).into(binding.ivBanner)
            binding.root.setOnClickListener {
                onItemClick.invoke(article)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ArticleVH {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemNewsBigBinding.inflate(inflater, parent, false)
                return ArticleVH(binding)
            }
        }
    }
}