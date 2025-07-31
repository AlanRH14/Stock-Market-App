package com.example.stockmarketapp.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object CompanyListings: Screen
    @Serializable
    data object CompanyInfo: Screen
}