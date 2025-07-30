package com.example.stockmarketapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IntradayInfoDto(
    @SerialName("timestamp")
    val timestamp: String,
    @SerialName("close")
    val close: Double
)