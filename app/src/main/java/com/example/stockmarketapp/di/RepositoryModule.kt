package com.example.stockmarketapp.di

import com.example.stockmarketapp.data.repository.StockRepositoryImpl
import com.example.stockmarketapp.domain.repository.StockRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single<StockRepository> {
        StockRepositoryImpl(
            api = get(),
            dao = get(),
            companyListingsParser = get(named("CompanyListingsParser")),
            intradayInfoParser = get(named("IntradayInfoParser")),
            companyEntityMapper = get(named("CompanyEntityMapper")),
            companyDomainMapper = get(named("CompanyDomainMapper")),
            companyInfoApiMapper = get(named("CompanyInfoApiMapper"))
        )
    }
}