package com.example.stockmarketapp.di

import com.example.stockmarketapp.data.csv.CSVParser
import com.example.stockmarketapp.data.csv.CompanyListingsParser
import com.example.stockmarketapp.data.csv.IntradayInfoParser
import com.example.stockmarketapp.data.repository.StockRepositoryImpl
import com.example.stockmarketapp.domain.model.CompanyListing
import com.example.stockmarketapp.domain.model.IntradayInfo
import com.example.stockmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.dsl.module
import javax.inject.Singleton

val repositoryModule = module {
    single<CSVParser<CompanyListing>> { CompanyListingsParser() }

    single<CSVParser<IntradayInfo>> { IntradayInfoParser() }

    single<StockRepository> {
        StockRepositoryImpl(
            api = get(),
            dao = get(),
            companyListingsParser = get(),
            intradayInfoParser = get()
        )
    }
}