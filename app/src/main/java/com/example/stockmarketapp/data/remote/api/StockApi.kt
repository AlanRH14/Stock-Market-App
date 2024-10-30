package com.example.stockmarketapp.data.remote.api

import com.example.stockmarketapp.data.remote.api.ApiConstants.API_KEY
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getStockListings(
        @Query("apikey") apikey : String = API_KEY
    ): ResponseBody
}