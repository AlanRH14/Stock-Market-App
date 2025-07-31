package com.example.stockmarketapp.presentation.company_info

interface CompanyInfoUIEvent {
    data class OnGetCompanyInfo(val symbol: String) : CompanyInfoUIEvent
    data class OnGetIntradayInfo(val symbol: String) : CompanyInfoUIEvent
}