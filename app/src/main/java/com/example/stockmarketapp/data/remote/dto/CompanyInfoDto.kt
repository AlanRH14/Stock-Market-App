package com.example.stockmarketapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyInfoDto(
    @SerialName("Symbol") val symbol: String? = null,
    @SerialName("Description") val description: String? = null,
    @SerialName("Name") val name: String? = null,
    @SerialName("Country") val country: String? = null,
    @SerialName("Industry") val industry: String? = null,
)
