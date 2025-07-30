package com.example.stockmarketapp.presentation.company_listing

interface CompanyListingEffect {
    data class NavigateToCompanyInfo(val symbol: String) : CompanyListingEffect
}