package com.example.stockmarketapp.presentation.company_listing

sealed interface CompanyListingsEvent {
    data object Refresh: CompanyListingsEvent
    data class OnSearchQueryChange(val query: String): CompanyListingsEvent
    data object GetCompanyListings: CompanyListingsEvent
}