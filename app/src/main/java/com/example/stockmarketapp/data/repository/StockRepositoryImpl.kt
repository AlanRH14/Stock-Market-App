package com.example.stockmarketapp.data.repository

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

    override suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val localListings = dao.searchCompanyListing(query = query)
            emit(Resource.Success(data = localListings.map { companyEntityMapper.mapToDomain(it) }))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(isLoading = false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getStockListings()
                companyListingsParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(data = null, message = "Couldn´t load data"))
                null
            } catch (e: HttpException) {
                emit(Resource.Error(data = null, message = "Couldn´t load data"))
                null
            }

            remoteListings?.let { listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map { companyDomainMapper.mapToDomain(it) }
                )
                emit(
                    Resource.Success(
                        data = dao
                            .searchCompanyListing("")
                            .map { companyEntityMapper.mapToDomain(it) }
                    ))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getIntradayInfo(symbol = symbol)
            val result = intradayInfoParser.parse(response.byteStream())
            Resource.Success(data = result)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                data = null,
                message = "Couldn't load intraday info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                data = null,
                message = "Couldn't load intraday info"
            )
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val response = api.getCompanyInfo(symbol = symbol)
            Resource.Success(companyInfoApiMapper.mapToDomain(response))
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                data = null,
                message = "Couldn't load company info"
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                data = null,
                message = "Couldn't load company info"
            )
        }
    }
}