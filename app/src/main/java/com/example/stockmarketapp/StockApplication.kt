package com.example.stockmarketapp

import android.app.Application
import com.example.stockmarketapp.di.appModule
import com.example.stockmarketapp.di.csvParserModule
import com.example.stockmarketapp.di.databaseModule
import com.example.stockmarketapp.di.networkModule
import com.example.stockmarketapp.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class StockApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@StockApplication)
            androidLogger(Level.DEBUG)
            modules(
                networkModule,
                csvParserModule,
                databaseModule,
                repositoryModule,
                appModule
            )
        }
    }
}