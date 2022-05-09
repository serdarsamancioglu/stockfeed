package com.serdar.stockfeed

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.serdar.stockfeed.databinding.ActivityMainBinding
import com.serdar.stockfeed.news.NewsFragment
import com.serdar.stockfeed.stocks.StocksFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        addFragment(R.id.fl_stocks, StocksFragment())
        addFragment(R.id.fl_news, NewsFragment())
    }

    private fun addFragment(@IdRes container: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(container, fragment).commit()
    }
}