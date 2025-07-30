package com.example.stockmarketapp.di

import androidx.room.Room
import com.example.stockmarketapp.data.local.StockDatabase
import com.example.stockmarketapp.data.remote.api.ApiConstants.BASE_URL
import com.example.stockmarketapp.data.remote.api.StockApi
import com.example.stockmarketapp.domain.utils.Constants.DB_NAME
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val appModule = module {


    single {
        Room.databaseBuilder(
            androidContext(),
            StockDatabase::class.java,
            DB_NAME
        ).build()
    }

    single { get<StockDatabase>().stockDao() }
}