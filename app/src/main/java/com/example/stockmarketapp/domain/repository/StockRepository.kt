package com.example.stockmarketapp.domain.repository

import com.example.stockmarketapp.domain.CompanyInfo
import com.example.stockmarketapp.domain.model.CompanyListing
import com.example.stockmarketapp.domain.model.IntradayInfo
import com.example.stockmarketapp.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    fun getIntradayInfo(
        symbol: String
    ): Flow<Resource<List<IntradayInfo>>>

    fun getCompanyInfo(
        symbol: String
    ): Flow<Resource<CompanyInfo>>
}