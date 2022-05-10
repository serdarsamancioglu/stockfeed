package com.serdar.stockfeed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.serdar.stockfeed.DATE_SPLITTER
import com.serdar.stockfeed.data.Article
import com.serdar.stockfeed.databinding.ItemNewsVerticalBinding


class VerticalNewsAdapter(private val articles: List<Article>, private val onItemClick: ((item: Article) -> Unit))
    : RecyclerView.Adapter<VerticalNewsAdapter.ArticleVH>() {

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ArticleVH, position: Int) {
        holder.bind(articles[position], onItemClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleVH {
        return ArticleVH.from(parent)
    }

    class ArticleVH(private val binding: ItemNewsVerticalBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article, onItemClick: (item: Article) -> Unit) {
            article.publishedAt?.let {
                binding.date = it.split(DATE_SPLITTER)[0]
            }
            binding.article = article
            val options = RequestOptions()
                .override(50, 50)
            Glide.with(binding.root.context)
                .asBitmap()
                .apply(options)
                .load(article.urlToImage)
                .into(binding.ivBanner)

            binding.root.setOnClickListener {
                onItemClick.invoke(article)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ArticleVH {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemNewsVerticalBinding.inflate(inflater, parent, false)
                binding.root.layoutParams.width = parent.width
                return ArticleVH(binding)
            }
        }
    }
}