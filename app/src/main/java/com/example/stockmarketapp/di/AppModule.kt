package com.example.stockmarketapp.di

import android.app.Application
import androidx.room.Room
import com.example.stockmarketapp.data.local.StockDatabase
import com.example.stockmarketapp.data.remote.api.ApiConstants.BASE_URL
import com.example.stockmarketapp.data.remote.api.StockApi
import com.example.stockmarketapp.domain.utils.Constants.DB_NAME
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesStockApi(): StockApi =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
            .create()

    @Provides
    @Singleton
    fun providesStockDatabase(app: Application): StockDatabase =
        Room.databaseBuilder(
            app,
            StockDatabase::class.java,
            DB_NAME
        ).build()
}