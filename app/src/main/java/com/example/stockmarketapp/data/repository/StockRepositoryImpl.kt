package com.example.stockmarketapp.data.repository

import android.util.Log
import com.example.stockmarketapp.common.ApiMapper
import com.example.stockmarketapp.common.CSVParser
import com.example.stockmarketapp.data.local.CompanyListingEntity
import com.example.stockmarketapp.data.local.StockDao
import com.example.stockmarketapp.data.remote.api.StockApi
import com.example.stockmarketapp.data.remote.dto.CompanyInfoDto
import com.example.stockmarketapp.domain.CompanyInfo
import com.example.stockmarketapp.domain.model.CompanyListing
import com.example.stockmarketapp.domain.model.IntradayInfo
import com.example.stockmarketapp.domain.repository.StockRepository
import com.example.stockmarketapp.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class StockRepositoryImpl(
    private val api: StockApi,
    private val dao: StockDao,
    private val companyListingsParser: CSVParser<CompanyListing>,
    private val intradayInfoParser: CSVParser<IntradayInfo>,
    private val companyEntityMapper: ApiMapper<CompanyListingEntity, CompanyListing>,
    private val companyDomainMapper: ApiMapper<CompanyListing, CompanyListingEntity>,
    private val companyInfoApiMapper: ApiMapper<CompanyInfoDto, CompanyInfo>
) : StockRepository {

    override fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> = flow {
        emit(Resource.Loading)
        val localListings = dao.searchCompanyListing(query = query)
        emit(Resource.Success(data = localListings.map { companyEntityMapper.mapToDomain(it) }))

        val isDbEmpty = localListings.isEmpty() && query.isBlank()
        val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
        if (shouldJustLoadFromCache) {
            return@flow
        }

        val remoteListings = try {
            val response = api.getStockListings()
            companyListingsParser.parse(response.byteStream())
        } catch (e: Exception) {
            throw Exception("${e.message}")
        }

        if (remoteListings.isNotEmpty()) {
            dao.clearCompanyListings()
            dao.insertCompanyListings(
                remoteListings.map { companyDomainMapper.mapToDomain(it) }
            )
            emit(
                Resource.Success(
                    data = dao
                        .searchCompanyListing("")
                        .map { companyEntityMapper.mapToDomain(it) })
            )
        }
    }.catch { error ->
        emit(Resource.Error(data = null, message = "Error: ${error.message}"))
    }

    override fun getIntradayInfo(symbol: String): Flow<Resource<List<IntradayInfo>>> = flow {
        emit(Resource.Loading)
        val response = api.getIntradayInfo(symbol = symbol)
        val result = intradayInfoParser.parse(response.byteStream())
        emit(Resource.Success(data = result))
    }.catch { error ->
        emit(
            Resource.Error(
                data = null,
                message = "Error: ${error.message}"
            )
        )
    }

    override fun getCompanyInfo(symbol: String): Flow<Resource<CompanyInfo>> = flow {
        emit(Resource.Loading)
        val response = api.getCompanyInfo(symbol = symbol)
        emit(Resource.Success(companyInfoApiMapper.mapToDomain(response)))
    }.catch { error ->
        emit(
            Resource.Error(
                data = null,
                message = "Error: ${error.message}"
            )
        )
    }
}