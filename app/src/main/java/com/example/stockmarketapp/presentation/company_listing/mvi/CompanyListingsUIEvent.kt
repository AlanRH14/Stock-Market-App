package com.example.stockmarketapp.presentation.company_listing.mvi

sealed interface CompanyListingsUIEvent {
    data object OnRefresh : CompanyListingsUIEvent
    data class OnSearchQueryChange(val query: String) : CompanyListingsUIEvent
    data object OnGetCompanyListingsUI : CompanyListingsUIEvent
    data class OnCompanyItemClickedUI(val symbol: String) : CompanyListingsUIEvent
}