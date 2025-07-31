package com.example.stockmarketapp.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object CompanyListings: Route
    @Serializable
    data object CompanyInfo: Route
}