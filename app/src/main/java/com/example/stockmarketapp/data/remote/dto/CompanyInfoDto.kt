package com.example.stockmarketapp.data.remote.dto

import com.squareup.moshi.Json

data class CompanyInfoDto(
    @Json(name = "Symbol") val symbol: String?,
    @Json(name = "Description") val description: String?,
    @Json(name = "Name") val name: String?,
    @Json(name = "Country") val country: String?,
    @Json(name = "Industry") val industry: String?,
)
