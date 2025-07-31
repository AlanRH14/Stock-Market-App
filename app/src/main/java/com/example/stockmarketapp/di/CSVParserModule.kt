package com.example.stockmarketapp.di

import com.example.stockmarketapp.common.CSVParser
import com.example.stockmarketapp.data.csv.CompanyListingsParser
import com.example.stockmarketapp.data.csv.IntradayInfoParser
import com.example.stockmarketapp.domain.model.CompanyListing
import com.example.stockmarketapp.domain.model.IntradayInfo
import org.koin.core.qualifier.named
import org.koin.dsl.module

val csvParserModule = module {
    single<CSVParser<CompanyListing>>(named("CompanyListingsParser")) { CompanyListingsParser() }

    single<CSVParser<IntradayInfo>>(named("IntradayInfoParser")) { IntradayInfoParser(get(named("IntradayInfoApiMapper"))) }
}