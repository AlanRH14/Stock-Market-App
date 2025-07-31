package com.example.stockmarketapp.presentation.company_info

interface CompanyInfoUIEvent {
    data object OnGetCompanyInfo : CompanyInfoUIEvent
    data object OnGetIntradayInfo : CompanyInfoUIEvent
}