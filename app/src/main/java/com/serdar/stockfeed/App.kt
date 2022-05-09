package com.serdar.stockfeed

import android.app.Application
import com.serdar.stockfeed.koin.repositoryModule
import com.serdar.stockfeed.koin.viewModelModule
import com.serdar.stockfeed.koin.networkModule
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }
}