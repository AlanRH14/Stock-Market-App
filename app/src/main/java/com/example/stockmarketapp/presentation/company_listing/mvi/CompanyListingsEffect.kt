package com.example.stockmarketapp.presentation.company_listing.mvi

interface CompanyListingsEffect {
    data class NavigateToCompanyInfo(val symbol: String) : CompanyListingsEffect
}