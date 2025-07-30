package com.example.stockmarketapp.presentation.company_listing

sealed interface CompanyListingsEvent {
    data object OnRefresh: CompanyListingsEvent
    data class OnSearchQueryChange(val query: String): CompanyListingsEvent
    data object GetCompanyListings: CompanyListingsEvent
    data class OnNavigateToCompanyInfo(val symbol: String): CompanyListingsEvent
}