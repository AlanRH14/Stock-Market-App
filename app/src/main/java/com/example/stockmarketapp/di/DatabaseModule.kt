package com.example.stockmarketapp.di

import androidx.room.Room
import com.example.stockmarketapp.data.local.StockDatabase
import com.example.stockmarketapp.domain.utils.Constants.DB_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            StockDatabase::class.java,
            DB_NAME
        ).build()
    }

    single { get<StockDatabase>().stockDao() }
}