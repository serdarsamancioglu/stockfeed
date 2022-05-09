package com.serdar.stockfeed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.serdar.stockfeed.USD
import com.serdar.stockfeed.databinding.ItemStockBinding

class StocksAdapter(private val stocks: List<Pair<String, MutableLiveData<String>>>, private val owner: LifecycleOwner)
    : RecyclerView.Adapter<StocksAdapter.StockVH>() {

    override fun getItemCount(): Int = stocks.size

    override fun onBindViewHolder(holder: StockVH, position: Int) {
        holder.bind(stocks[position].first, stocks[position].second, owner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockVH {
        return StockVH.from(parent)
    }

    class StockVH(private val binding: ItemStockBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(name: String, liveData: LiveData<String>, owner: LifecycleOwner) {
            binding.name = name
            liveData.observe(owner) { price ->
                binding.price = price.plus(USD)
            }
        }

        companion object {
            fun from(parent: ViewGroup): StockVH {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemStockBinding.inflate(inflater, parent, false)
                return StockVH(binding)
            }
        }
    }
}