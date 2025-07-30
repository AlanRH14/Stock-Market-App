package com.example.stockmarketapp.data.repository

import com.example.stockmarketapp.data.csv.CSVParser
import com.example.stockmarketapp.data.local.StockDao
import com.example.stockmarketapp.data.mapper.toCompanyInfo
import com.example.stockmarketapp.data.mapper.toCompanyListing
import com.example.stockmarketapp.data.mapper.toCompanyListingEntity
import com.example.stockmarketapp.data.remote.api.StockApi
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
    private val intradayInfoParser: CSVParser<IntradayInfo>
) : StockRepository {

    override suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val localListings = dao.searchCompanyListing(query = query)
            emit(
                Resource.Success(
                    data = localListings.map { it.toCompanyListing() }
                )
            )

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
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(
                    Resource.Success(
                        data = dao
                            .searchCompanyListing("")
                            .map { it.toCompanyListing() }
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
            Resource.Success(response.toCompanyInfo())
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