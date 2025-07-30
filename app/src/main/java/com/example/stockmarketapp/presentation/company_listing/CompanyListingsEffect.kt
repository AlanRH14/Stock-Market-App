package com.example.stockmarketapp.presentation.company_listing

interface CompanyListingsEffect {
    data class NavigateToCompanyInfo(val symbol: String) : CompanyListingsEffect
}